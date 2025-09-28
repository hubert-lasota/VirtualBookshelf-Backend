package org.hl.wirtualnyregalbackend.reading_note.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record ReadingNoteRequest(
    @NotNull(groups = CreateGroup.class)
    Long readingBookId, // this field is not used in update
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(50)
    String title,
    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowNotTrimmed = true)
    String content,
    @NotNull(groups = CreateGroup.class)
    @Valid
    PageRange pageRange
) {
}
