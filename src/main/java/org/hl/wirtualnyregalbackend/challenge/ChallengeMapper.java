package org.hl.wirtualnyregalbackend.challenge;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeMutationDto;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponseDto;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

class ChallengeMapper {

    private ChallengeMapper() {
    }

    public static Challenge toChallenge(ChallengeMutationDto challengeDto,
                                        Genre genre,
                                        User user) {
        return new Challenge(
            challengeDto.title(),
            challengeDto.description(),
            challengeDto.startAt(),
            challengeDto.endAt(),
            challengeDto.type(),
            challengeDto.targetCount(),
            genre,
            user
        );
    }

    public static ChallengeResponseDto toChallengeResponseDto(Challenge challenge,
                                                              GenreResponseDto genreDto,
                                                              Long totalParticipants) {
        return new ChallengeResponseDto(
            challenge.getId(),
            challenge.getTitle(),
            challenge.getDescription(),
            challenge.getType(),
            challenge.getTargetCount(),
            challenge.getStartAt(),
            challenge.getEndAt(),
            genreDto,
            totalParticipants
        );
    }
}
