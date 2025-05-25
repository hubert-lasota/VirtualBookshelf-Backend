package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfType;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;

@NotAllFieldsNull(groups = UpdateGroup.class)
public record BookshelfMutationDto(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String name,

    @NotNull(groups = CreateGroup.class)
    BookshelfType type,

    @StringConstraints
    String description
) {
}
