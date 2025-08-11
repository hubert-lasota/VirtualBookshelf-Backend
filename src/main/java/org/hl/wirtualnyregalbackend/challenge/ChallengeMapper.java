package org.hl.wirtualnyregalbackend.challenge;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeMutationDto;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeParticipation;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponseDto;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserResponseDto;
import org.springframework.lang.Nullable;

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
                                                              @Nullable ChallengeParticipant currentUserParticipant,
                                                              @Nullable GenreResponseDto genreDto,
                                                              Long totalParticipants) {
        Integer targetCount = challenge.getTargetCount();
        ChallengeParticipation participation = toChallengeParticipation(currentUserParticipant, targetCount);
        UserResponseDto user = UserMapper.toUserResponseDto(challenge.getUser());
        return new ChallengeResponseDto(
            challenge.getId(),
            challenge.getTitle(),
            challenge.getDescription(),
            challenge.getType(),
            targetCount,
            challenge.getStartAt(),
            challenge.getEndAt(),
            genreDto,
            totalParticipants,
            participation,
            user
        );
    }

    private static ChallengeParticipation toChallengeParticipation(@Nullable ChallengeParticipant participant, Integer targetCount) {
        if (participant == null) {
            return new ChallengeParticipation(false, null, null, null, null, null);
        }
        Integer currentCount = participant.getCurrentCount();
        return new ChallengeParticipation(
            true,
            currentCount,
            calculateProgressPercentage(currentCount, targetCount),
            participant.getStatus(),
            participant.getStartedAt(),
            participant.getFinishedAt()
        );
    }

    private static Float calculateProgressPercentage(Integer currentCount, Integer targetCount) {
        if (currentCount.equals(0)) {
            return 0F;
        }
        return ((float) currentCount) / targetCount * 100F;
    }

}
