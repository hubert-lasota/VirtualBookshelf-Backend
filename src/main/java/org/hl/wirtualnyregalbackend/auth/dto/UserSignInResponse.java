package org.hl.wirtualnyregalbackend.auth.dto;

import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;

import java.util.List;

public record UserSignInResponse(
    Long id,
    String username,
    String jwt,
    UserProfileDto profile,
    List<GenreResponse> genrePreferences
) {
}
