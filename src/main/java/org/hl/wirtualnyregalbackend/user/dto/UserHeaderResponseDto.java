package org.hl.wirtualnyregalbackend.user.dto;

public record UserHeaderResponseDto(
    Long id,
    String username,
    String profilePictureUrl
) {
}
