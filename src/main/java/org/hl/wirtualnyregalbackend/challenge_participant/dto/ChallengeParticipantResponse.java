package org.hl.wirtualnyregalbackend.challenge_participant.dto;

import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;

public record ChallengeParticipantResponse(
    Long id,
    Integer currentGoalValue,
    Float progressPercentage,
    ChallengeParticipantStatus status,
    ChallengeParticipantDurationRange durationRange,
    UserResponse user,
    ChallengeSummaryResponse challenge
) {
}
