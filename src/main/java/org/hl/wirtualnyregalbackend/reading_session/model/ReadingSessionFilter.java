package org.hl.wirtualnyregalbackend.reading_session.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.model.InstantRangeFilter;
import org.hl.wirtualnyregalbackend.common.model.NumberRangeFilter;
import org.springframework.lang.Nullable;

public record ReadingSessionFilter(
    @NotNull
    Long readingBookId,
    @Nullable
    String query,
    @Nullable
    @Valid
    InstantRangeFilter durationRange,
    @Nullable
    @Valid
    NumberRangeFilter pageRange
) {
}
