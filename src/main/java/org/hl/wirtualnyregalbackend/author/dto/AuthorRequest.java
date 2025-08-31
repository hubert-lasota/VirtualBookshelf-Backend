package org.hl.wirtualnyregalbackend.author.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;


public record AuthorRequest(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String fullName,
    @URL
    String profilePictureUrl,
    @StringConstraints
    String description
) {
}
