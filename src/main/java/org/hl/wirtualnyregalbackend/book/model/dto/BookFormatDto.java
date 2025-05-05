package org.hl.wirtualnyregalbackend.book.model.dto;

import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.book.model.dto.group.BookFormatCreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record BookFormatDto(
    Long id,

    @NotNull(groups = BookFormatCreateGroup.class)
    @StringConstraints
    String name) {
}
