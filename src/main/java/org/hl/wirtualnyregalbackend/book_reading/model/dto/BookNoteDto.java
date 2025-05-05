package org.hl.wirtualnyregalbackend.book_reading.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record BookNoteDto(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowNotTrimmed = true)
    String content,

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer startPage,

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer endPage) {
}
