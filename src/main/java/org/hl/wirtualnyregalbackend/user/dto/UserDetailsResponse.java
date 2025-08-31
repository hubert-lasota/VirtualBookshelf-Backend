package org.hl.wirtualnyregalbackend.user.dto;

import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;

import java.time.Instant;
import java.util.List;

public record UserDetailsResponse(
    Long id,
    String username,
    UserProfileDto profile,
    List<GenreResponse> preferredGenres,
    Instant createdAt,
    Instant updatedAt
) {
}
