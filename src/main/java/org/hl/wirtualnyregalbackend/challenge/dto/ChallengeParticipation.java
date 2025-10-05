package org.hl.wirtualnyregalbackend.challenge.dto;

import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;

public record ChallengeParticipation(
    boolean participates,
    int currentGoalValue,
    float progressPercentage,
    ChallengeParticipantStatus status,
    ChallengeParticipantDurationRange durationRange
) {
}
