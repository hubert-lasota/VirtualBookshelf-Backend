package org.hl.wirtualnyregalbackend.challenge_participant;

import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantResponse;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeSummaryResponse;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;

import java.util.Locale;

class ChallengeParticipantMapper {

    private ChallengeParticipantMapper() {
    }


    public static ChallengeParticipantResponse toChallengeParticipantResponse(ChallengeParticipant challengeParticipant,
                                                                              Locale locale) {
        UserResponse user = UserMapper.toUserResponse(challengeParticipant.getUser(), locale);
        Challenge ch = challengeParticipant.getChallenge();
        ChallengeSummaryResponse challenge = new ChallengeSummaryResponse(ch.getId(), ch.getTitle());
        return new ChallengeParticipantResponse(
            challengeParticipant.getId(),
            challengeParticipant.getCurrentGoalValue(),
            challengeParticipant.calculateProgressPercentage(),
            challengeParticipant.getStatus(),
            challengeParticipant.getDurationRange(),
            user,
            challenge
        );
    }
}
