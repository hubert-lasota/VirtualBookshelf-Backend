package org.hl.wirtualnyregalbackend.challenge.dto;

import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;

public record ChallengeParticipation(
    int currentGoalValue,
    float progressPercentage,
    ChallengeParticipantStatus status,
    ChallengeParticipantDurationRange durationRange
) {
}
