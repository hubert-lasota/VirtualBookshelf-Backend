package org.hl.wirtualnyregalbackend.challenge;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeParticipation;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeRequest;
import org.hl.wirtualnyregalbackend.challenge.dto.ChallengeResponse;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.springframework.lang.Nullable;

import java.util.Locale;

public class ChallengeMapper {

    private ChallengeMapper() {
    }

    public static Challenge toChallenge(ChallengeRequest challengeRequest,
                                        Genre genre,
                                        User user) {
        return new Challenge(
            challengeRequest.title(),
            challengeRequest.description(),
            challengeRequest.durationRange(),
            challengeRequest.type(),
            challengeRequest.goalValue(),
            genre,
            user
        );
    }

    public static ChallengeResponse toChallengeResponse(Challenge challenge,
                                                        @Nullable ChallengeParticipant currentUserParticipant,
                                                        Locale locale) {
        ChallengeParticipation participation = toChallengeParticipation(currentUserParticipant);
        UserResponse user = UserMapper.toUserResponse(challenge.getUser(), locale);
        GenreResponse genreDto = challenge.getGenre() == null
            ? null
            : GenreMapper.toGenreResponse(challenge.getGenre(), locale);
        return new ChallengeResponse(
            challenge.getId(),
            challenge.getTitle(),
            challenge.getDescription(),
            challenge.getType(),
            challenge.getGoalValue(),
            challenge.getDurationRange(),
            genreDto,
            challenge.getTotalParticipants(),
            participation,
            user
        );
    }

    @Nullable
    private static ChallengeParticipation toChallengeParticipation(@Nullable ChallengeParticipant participant) {
        if (participant == null) {
            return null;
        }
        return new ChallengeParticipation(
            participant.getCurrentGoalValue(),
            participant.calculateProgressPercentage(),
            participant.getStatus(),
            participant.getDurationRange()
        );
    }

}
