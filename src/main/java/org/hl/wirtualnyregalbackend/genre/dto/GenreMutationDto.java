package org.hl.wirtualnyregalbackend.genre.dto;

import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record GenreMutationDto(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String name
) {
}
