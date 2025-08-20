package org.hl.wirtualnyregalbackend.challenge.dto;

import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;

public record ChallengeParticipation(
    boolean participates,
    Integer currentGoalValue,
    Float progressPercentage,
    ChallengeParticipantStatus status,
    ChallengeParticipantDurationRange durationRange
) {
}
