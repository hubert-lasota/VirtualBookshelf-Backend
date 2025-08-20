package org.hl.wirtualnyregalbackend.challenge.dto;

import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;

import java.time.Instant;

public record ChallengeResponse(
    Long id,
    String title,
    String description,
    ChallengeType type,
    Integer targetCount,
    Instant startAt,
    Instant endAt,
    GenreResponse genre,
    Long totalParticipants,
    ChallengeParticipation participation,
    UserResponse user
) {
}
