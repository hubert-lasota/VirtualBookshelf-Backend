package org.hl.wirtualnyregalbackend.auth.dto;

import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;

public record UserSignInResponse(
    Long id,
    String username,
    String jwt,
    UserProfileDto profile
) {
}
