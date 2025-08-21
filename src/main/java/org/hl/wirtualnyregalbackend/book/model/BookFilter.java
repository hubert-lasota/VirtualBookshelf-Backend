package org.hl.wirtualnyregalbackend.book.model;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.common.model.RangeFilter;
import org.springframework.lang.Nullable;

public record BookFilter(
    @Nullable
    @Valid
    RangeFilter publicationYearRange,
    @Nullable
    @Valid
    RangeFilter pageCountRange,
    @Nullable
    Long authorId,
    @Nullable
    Long genreId,
    @Nullable
    Long formatId,
    @Nullable
    Long publisherId
) {
}
