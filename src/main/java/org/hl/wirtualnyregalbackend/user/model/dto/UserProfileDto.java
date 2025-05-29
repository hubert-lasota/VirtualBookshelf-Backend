package org.hl.wirtualnyregalbackend.user.model.dto;

public record UserProfileDto(
    String firstName,
    String lastName,
    String pictureUrl,
    String description
) {
}
