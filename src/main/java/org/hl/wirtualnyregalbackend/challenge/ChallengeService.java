package org.hl.wirtualnyregalbackend.challenge;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengePageResponse;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeRequest;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponse;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeDurationRange;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeFilter;
import org.hl.wirtualnyregalbackend.challenge_participant.ChallengeParticipantService;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.genre.GenreService;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeHelper challengeHelper;
    private final ChallengeParticipantService participantService;
    private final GenreService genreService;

    @Transactional
    public ChallengeResponse createChallenge(ChallengeRequest challengeRequest, User user) {
        Genre genre = challengeRequest.genreId() == null
            ? null
            : genreService.findGenreById(challengeRequest.genreId());
        Challenge challenge = ChallengeMapper.toChallenge(challengeRequest, genre, user);
        challengeRepository.save(challenge);
        ChallengeParticipant participant = participantService.createChallengeParticipant(challenge);
        log.info("Created challenge: {} and participant: {}", challenge, participant);
        return mapToChallengeResponse(challenge);
    }

    public ChallengeResponse updateChallenge(Long challengeId, ChallengeRequest challengeRequest) {
        Challenge challenge = challengeHelper.findChallengeById(challengeId);
        log.info("Updating challenge: {} by request: {}", challenge, challengeRequest);
        String title = challengeRequest.title();
        if (title != null) {
            challenge.setTitle(title);
        }
        String description = challengeRequest.description();
        if (description != null) {
            challenge.setDescription(description);
        }
        Long genreId = challengeRequest.genreId();
        if (genreId != null) {
            Genre genre = genreService.findGenreById(genreId);
            challenge.setGenre(genre);
        }

        Integer goalValue = challengeRequest.goalValue();
        if (goalValue != null) {
            challenge.setGoalValue(goalValue);
        }

        ChallengeDurationRange oldDr = challenge.getDurationRange();
        ChallengeDurationRange newDr = challengeRequest.durationRange();
        challenge.setDurationRange(ChallengeDurationRange.merge(oldDr, newDr));

        challengeRepository.save(challenge);
        log.info("Updated challenge: {}", challenge);
        return mapToChallengeResponse(challenge);
    }

    public void quitChallenge(Long challengeId, User user) {
        ChallengeParticipant participant = participantService.findParticipantByChallengeIdAndUserId(challengeId, user.getId());
        log.info("Quit challenge: {} with participant: {}", challengeId, participant);
        participantService.deleteParticipant(participant);
    }

    public ChallengePageResponse findChallenges(ChallengeFilter filter, User participant, Pageable pageable) {
        var spec = ChallengeSpecification.byFilterAndParticipant(filter, participant);
        var page = challengeRepository.findAll(spec, pageable);
        List<Long> challengeIds = page
            .getContent()
            .stream()
            .map(Challenge::getId)
            .toList();
        Map<Long, ChallengeParticipant> participants = participantService.findCurrentUserParticipantsByChallengeIds(challengeIds, participant);
        Locale locale = LocaleContextHolder.getLocale();
        var responsePage = page.map((challenge) -> {
            var p = participants.get(challenge.getId());
            return ChallengeMapper.toChallengeResponse(challenge, p, locale);
        });
        return ChallengePageResponse.from(responsePage);
    }

    public boolean isChallengeAuthor(Long challengeId, User user) {
        return challengeRepository.isChallengeAuthor(challengeId, user.getId());
    }

    public Slice<Challenge> findChallengesByEndAt(LocalDate endAt, Pageable pageable) {
        return challengeRepository.findByEndAt(endAt, pageable);
    }

    private ChallengeResponse mapToChallengeResponse(Challenge challenge) {
        Locale locale = LocaleContextHolder.getLocale();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ChallengeParticipant participant = participantService.findParticipantByChallengeIdAndUserId(challenge.getId(), user.getId());
        return ChallengeMapper.toChallengeResponse(challenge, participant, locale);
    }

}
