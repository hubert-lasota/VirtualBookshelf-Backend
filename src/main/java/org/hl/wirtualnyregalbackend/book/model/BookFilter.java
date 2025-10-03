package org.hl.wirtualnyregalbackend.book.model;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.common.model.IntegerRangeFilter;
import org.springframework.lang.Nullable;

public record BookFilter(
    @Nullable
    String query,
    @Nullable
    @Valid
    IntegerRangeFilter publicationYearRange,
    @Nullable
    @Valid
    IntegerRangeFilter pageCountRange,
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
