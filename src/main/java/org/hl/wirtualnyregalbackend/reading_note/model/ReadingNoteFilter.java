package org.hl.wirtualnyregalbackend.reading_note.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.model.IntegerRangeFilter;
import org.springframework.lang.Nullable;


public record ReadingNoteFilter(
    @NotNull
    Long readingBookId,
    @Nullable
    String query,
    @Nullable
    @Valid
    IntegerRangeFilter pageRange
) {
}
