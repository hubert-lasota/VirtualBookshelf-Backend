package org.hl.wirtualnyregalbackend.user.dto;

public record UserResponseDto(
    Long id,
    String username,
    UserProfileDto profile
) {
}
