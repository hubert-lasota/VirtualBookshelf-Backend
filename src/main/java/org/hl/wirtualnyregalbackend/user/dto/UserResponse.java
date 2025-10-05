package org.hl.wirtualnyregalbackend.user.dto;

import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;

import java.util.List;

public record UserResponse(
    Long id,
    String username,
    UserProfileDto profile,
    List<GenreResponse> genrePreferences
) {
}
