package org.hl.wirtualnyregalbackend.user.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record UserProfileDto(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String firstName,
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String lastName,
    String description,
    @URL
    String pictureUrl
) {
}
