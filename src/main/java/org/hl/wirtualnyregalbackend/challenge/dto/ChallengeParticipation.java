package org.hl.wirtualnyregalbackend.challenge.dto;

import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipantStatus;

import java.time.Instant;

public record ChallengeParticipation(
    boolean participates,
    Integer currentCount,
    Float progressPercentage,
    ChallengeParticipantStatus status,
    Instant startedAt,
    Instant finishedAt
) {
}
