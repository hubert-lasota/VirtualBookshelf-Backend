package org.hl.wirtualnyregalbackend.user.dto;

public record UserProfileDto(
    String firstName,
    String lastName,
    String pictureUrl,
    String description
) {
}
