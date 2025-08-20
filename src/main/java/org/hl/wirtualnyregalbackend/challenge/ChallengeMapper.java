package org.hl.wirtualnyregalbackend.challenge;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeParticipation;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeRequest;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponse;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.springframework.lang.Nullable;

class ChallengeMapper {

    private ChallengeMapper() {
    }

    public static Challenge toChallenge(ChallengeRequest challengeRequest,
                                        Genre genre,
                                        User user) {
        return new Challenge(
            challengeRequest.title(),
            challengeRequest.description(),
            challengeRequest.startAt(),
            challengeRequest.endAt(),
            challengeRequest.type(),
            challengeRequest.goalValue(),
            genre,
            user
        );
    }

    public static ChallengeResponse toChallengeResponse(Challenge challenge,
                                                        @Nullable ChallengeParticipant currentUserParticipant,
                                                        @Nullable GenreResponse genreDto,
                                                        Long totalParticipants) {
        Integer goalValue = challenge.getGoalValue();
        ChallengeParticipation participation = toChallengeParticipation(currentUserParticipant, goalValue);
        UserResponse user = UserMapper.toUserResponse(challenge.getUser());
        return new ChallengeResponse(
            challenge.getId(),
            challenge.getTitle(),
            challenge.getDescription(),
            challenge.getType(),
            goalValue,
            challenge.getStartAt(),
            challenge.getEndAt(),
            genreDto,
            totalParticipants,
            participation,
            user
        );
    }

    private static ChallengeParticipation toChallengeParticipation(@Nullable ChallengeParticipant participant, Integer challengeGoalValue) {
        if (participant == null) {
            return new ChallengeParticipation(false, null, null, null, null);
        }
        Integer currentGoalValue = participant.getCurrentGoalValue();
        return new ChallengeParticipation(
            true,
            currentGoalValue,
            calculateProgressPercentage(currentGoalValue, challengeGoalValue),
            participant.getStatus(),
            participant.getDurationRange()
        );
    }

    private static Float calculateProgressPercentage(Integer currentGoalValue, Integer challengeGoalValue) {
        if (currentGoalValue.equals(0)) {
            return 0F;
        }
        return ((float) currentGoalValue) / challengeGoalValue * 100F;
    }

}
