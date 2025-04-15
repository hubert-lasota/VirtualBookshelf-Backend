package org.hl.wirtualnyregalbackend.user.model.dto;

public record UserHeaderResponseDto(
    Long id,
    String username,
    String profilePictureUrl
) {
}
