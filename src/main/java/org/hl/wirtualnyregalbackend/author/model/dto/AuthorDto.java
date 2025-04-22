package org.hl.wirtualnyregalbackend.author.model.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record AuthorDto(
    Long id,

    @NotNull(groups = AuthorCreateGroup.class)
    @StringConstraints
    String fullName,

    @NotNull(groups = AuthorCreateGroup.class)
    @URL
    String photoUrl,

    @StringConstraints
    String description
) {
}
