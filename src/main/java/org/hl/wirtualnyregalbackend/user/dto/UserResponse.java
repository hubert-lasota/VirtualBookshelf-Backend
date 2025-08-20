package org.hl.wirtualnyregalbackend.user.dto;

public record UserResponse(
    Long id,
    String username,
    UserProfileDto profile
) {
}
