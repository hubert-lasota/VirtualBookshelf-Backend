package org.hl.wirtualnyregalbackend.challenge;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeRequest;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponse;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.ChallengeParticipantCommandService;
import org.hl.wirtualnyregalbackend.challenge_participant.ChallengeParticipantQueryService;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.genre.GenreQueryService;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ChallengeCommandService {

    private final ChallengeRepository repository;
    private final ChallengeQueryService challengeQuery;
    private final GenreQueryService genreQuery;
    private final ChallengeParticipantQueryService participantQuery;
    private final ChallengeParticipantCommandService participantCommand;

    @Transactional
    public ChallengeResponse createChallenge(ChallengeRequest challengeRequest, User user) {
        Genre genre = challengeRequest.genreId() == null
            ? null
            : genreQuery.findGenreById(challengeRequest.genreId());
        Challenge challenge = ChallengeMapper.toChallenge(challengeRequest, genre, user);
        repository.save(challenge);
        ChallengeParticipant participant = participantCommand.createChallengeParticipant(challenge, user);
        log.info("Created challenge: {} and participant: {}", challenge, participant);
        return mapToChallengeResponse(challenge, participant);
    }

    @Transactional
    public ChallengeResponse updateChallenge(Long challengeId, ChallengeRequest challengeRequest) {
        Challenge challenge = challengeQuery.findChallengeById(challengeId);
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
            Genre genre = genreQuery.findGenreById(genreId);
            challenge.setGenre(genre);
        }

        Integer goalValue = challengeRequest.goalValue();
        if (goalValue != null) {
            challenge.setGoalValue(goalValue);
        }

        ChallengeDurationRange oldDr = challenge.getDurationRange();
        ChallengeDurationRange newDr = challengeRequest.durationRange();
        challenge.setDurationRange(ChallengeDurationRange.merge(oldDr, newDr));

        log.info("Updated challenge: {}", challenge);
        return mapToChallengeResponse(challenge);
    }

    @Transactional
    public ChallengeResponse joinChallenge(Long challengeId, User user) {
        Challenge challenge = challengeQuery.findChallengeById(challengeId);
        log.info("Joining challenge: {} with user: {}", challenge, user);
        ChallengeParticipant participant = participantCommand.createChallengeParticipant(challenge, user);
        return mapToChallengeResponse(challenge, participant);
    }

    public void quitChallenge(Long challengeId, User user) {
        ChallengeParticipant participant = participantQuery.findParticipantByChallengeIdAndUserId(challengeId, user.getId());
        log.info("Quit challenge: {} with participant: {}", challengeId, participant);
        participantCommand.deleteParticipant(participant);
    }


    private ChallengeResponse mapToChallengeResponse(Challenge challenge) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ChallengeParticipant participant = participantQuery.findParticipantByChallengeIdAndUserId(challenge.getId(), user.getId());
        return mapToChallengeResponse(challenge, participant);
    }

    private ChallengeResponse mapToChallengeResponse(Challenge challenge, ChallengeParticipant participant) {
        Locale locale = LocaleContextHolder.getLocale();
        return ChallengeMapper.toChallengeResponse(challenge, participant, locale);
    }

}
