package org.hl.wirtualnyregalbackend.bookshelf.dto;

import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;


public record BookshelfRequest(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String name,

    @NotNull(groups = CreateGroup.class)
    BookshelfType type,

    @StringConstraints
    String description
) {
}
