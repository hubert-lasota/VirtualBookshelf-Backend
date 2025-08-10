package org.hl.wirtualnyregalbackend.challenge.dto;

import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;

import java.time.Instant;

public record ChallengeResponseDto(
    Long id,
    String title,
    String description,
    ChallengeType type,
    Integer targetCount,
    Instant startAt,
    Instant endAt,
    GenreResponseDto genre,
    Long totalParticipants
) {
}
