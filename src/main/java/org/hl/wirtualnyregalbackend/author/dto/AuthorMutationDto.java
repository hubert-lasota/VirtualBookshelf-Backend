package org.hl.wirtualnyregalbackend.author.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record AuthorMutationDto(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String fullName,

    @NotNull(groups = CreateGroup.class)
    @URL
    String photoUrl,

    @StringConstraints
    String description
) {
}
