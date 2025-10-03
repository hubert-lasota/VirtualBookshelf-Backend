package org.hl.wirtualnyregalbackend.reading_session.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.model.InstantRange;
import org.hl.wirtualnyregalbackend.common.model.IntegerRange;
import org.springframework.lang.Nullable;

public record ReadingSessionFilter(
    @NotNull
    Long readingBookId,
    @Nullable
    String query,
    @Nullable
    @Valid
    InstantRange durationRange,
    @Nullable
    @Valid
    IntegerRange pageRange
) {
}
