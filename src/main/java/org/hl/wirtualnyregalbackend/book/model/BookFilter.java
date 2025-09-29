package org.hl.wirtualnyregalbackend.book.model;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.common.model.NumberRangeFilter;
import org.springframework.lang.Nullable;

public record BookFilter(
    @Nullable
    String query,
    @Nullable
    @Valid
    NumberRangeFilter publicationYearRange,
    @Nullable
    @Valid
    NumberRangeFilter pageCountRange,
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
