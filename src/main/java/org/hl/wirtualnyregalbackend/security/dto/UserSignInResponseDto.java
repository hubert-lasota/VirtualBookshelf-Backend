package org.hl.wirtualnyregalbackend.security.dto;

import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;

public record UserSignInResponseDto(
    Long id,
    String username,
    String jwt,
    UserProfileDto profile) {
}
