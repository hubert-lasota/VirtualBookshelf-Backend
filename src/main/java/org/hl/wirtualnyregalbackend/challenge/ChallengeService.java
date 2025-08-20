package org.hl.wirtualnyregalbackend.challenge;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengePageResponse;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeRequest;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponse;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeFilter;
import org.hl.wirtualnyregalbackend.challenge_participant.ChallengeParticipantHelper;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.GenreService;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeParticipantHelper participantHelper;
    private final GenreService genreService;

    public ChallengeResponse createChallenge(ChallengeRequest challengeRequest, User user) {
        Genre genre = challengeRequest.genreId() == null
            ? null
            : genreService.findGenreById(challengeRequest.genreId());
        Challenge challenge = ChallengeMapper.toChallenge(challengeRequest, genre, user);
        challengeRepository.save(challenge);
        participantHelper.createChallengeParticipant(challenge);
        return mapToChallengeResponseDto(challenge);
    }

    public ChallengeResponse updateChallenge(Long challengeId, ChallengeRequest challengeRequest) {
        Challenge challenge = findChallengeById(challengeId);
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
        return mapToChallengeResponseDto(challenge);
    }

    public Challenge findChallengeById(Long challengeId) {
        Optional<Challenge> challengeOpt = challengeId == null
            ? Optional.empty()
            : challengeRepository.findById(challengeId);
        return challengeOpt.orElseThrow(() -> new IllegalArgumentException("Challenge with id = '%d' not found.".formatted(challengeId)));
    }

    public ChallengePageResponse findChallenges(ChallengeFilter filter, User participant, Pageable pageable) {
        Specification<Challenge> spec = Specification.where(null);
        if (filter.participating() != null) {
            spec = ChallengeSpecification.byParticipant(participant);
        }

        Page<ChallengeResponse> page = challengeRepository
            .findAll(spec, pageable)
            .map(this::mapToChallengeResponseDto);

        return ChallengePageResponse.from(page);
    }

    public void quitChallenge(Long challengeId) {
        ChallengeParticipant participant = participantHelper.findCurrentUserParticipant(challengeId);
        if (participant == null) {
            throw new EntityNotFoundException("ChallengeParticipant not found for challengeId: %d and and current usr".formatted(challengeId));
        }
        participantHelper.deleteParticipant(participant);
    }

    private ChallengeResponse mapToChallengeResponseDto(Challenge challenge) {
        Locale locale = LocaleContextHolder.getLocale();
        GenreResponse genreDto = challenge.getGenre() == null
            ? null
            : GenreMapper.toGenreResponse(challenge.getGenre(), locale);
        Long totalParticipants = participantHelper.findTotalParticipantsByChallengeId(challenge.getId());
        ChallengeParticipant participant = participantHelper.findCurrentUserParticipant(challenge.getId());
        return ChallengeMapper.toChallengeResponse(challenge, participant, genreDto, totalParticipants);
    }

}
