package org.hl.wirtualnyregalbackend.challenge;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeMutationDto;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengePageResponseDto;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponseDto;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeFilter;
import org.hl.wirtualnyregalbackend.challenge_participant.ChallengeParticipantHelper;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.GenreService;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
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

    public ChallengeResponseDto createChallenge(ChallengeMutationDto challengeDto, User user) {
        Genre genre = challengeDto.genreId() == null
            ? null
            : genreService.findGenreById(challengeDto.genreId());
        Challenge challenge = ChallengeMapper.toChallenge(challengeDto, genre, user);
        challengeRepository.save(challenge);
        participantHelper.createChallengeParticipant(challenge);
        return mapToChallengeResponseDto(challenge);
    }

    public ChallengeResponseDto updateChallenge(Long challengeId, ChallengeMutationDto challengeDto) {
        Challenge challenge = findChallengeById(challengeId);
        String title = challengeDto.title();
        if (title != null) {
            challenge.setTitle(title);
        }
        String description = challengeDto.description();
        if (description != null) {
            challenge.setDescription(description);
        }
        Long genreId = challengeDto.genreId();
        if (genreId != null) {
            Genre genre = genreService.findGenreById(genreId);
            challenge.setGenre(genre);
        }

        Integer targetCount = challengeDto.targetCount();
        if (targetCount != null) {
            challenge.setTargetCount(targetCount);
        }
        return mapToChallengeResponseDto(challenge);
    }

    public Challenge findChallengeById(Long challengeId) {
        Optional<Challenge> challengeOpt = challengeId == null
            ? Optional.empty()
            : challengeRepository.findById(challengeId);
        return challengeOpt.orElseThrow(() -> new IllegalArgumentException("Challenge with id = '%d' not found.".formatted(challengeId)));
    }

    public ChallengePageResponseDto findChallenges(ChallengeFilter filter, User participant, Pageable pageable) {
        Specification<Challenge> spec = Specification.where(null);
        if (filter.participating() != null) {
            spec = ChallengeSpecification.byParticipant(participant);
        }

        Page<ChallengeResponseDto> page = challengeRepository
            .findAll(spec, pageable)
            .map(this::mapToChallengeResponseDto);

        return ChallengePageResponseDto.from(page);
    }

    public void deleteChallenge(Long challengeId) {
        challengeRepository.deleteById(challengeId);
    }

    private ChallengeResponseDto mapToChallengeResponseDto(Challenge challenge) {
        Locale locale = LocaleContextHolder.getLocale();
        GenreResponseDto genreDto = challenge.getGenre() == null
            ? null
            : GenreMapper.toGenreResponseDto(challenge.getGenre(), locale);
        Long totalParticipants = participantHelper.findTotalParticipantsByChallengeId(challenge.getId());
        ChallengeParticipant participant = participantHelper.findCurrentUserParticipant(challenge.getId());
        return ChallengeMapper.toChallengeResponseDto(challenge, participant, genreDto, totalParticipants);
    }

}
