package org.hl.wirtualnyregalbackend.genre.model.dto;

import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record GenreDto(
    Long id,

    @NotNull(groups = CreateGenreGroup.class)
    @StringConstraints
    String name
) {
}
