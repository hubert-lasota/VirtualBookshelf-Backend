package org.hl.wirtualnyregalbackend.security.model.dto;

import org.hl.wirtualnyregalbackend.user.model.dto.UserProfileDto;

public record UserSignInResponseDto(
    Long id,
    String username,
    String jwt,
    UserProfileDto profile) {
}
