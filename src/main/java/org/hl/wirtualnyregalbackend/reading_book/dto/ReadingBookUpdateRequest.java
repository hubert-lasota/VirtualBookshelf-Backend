package org.hl.wirtualnyregalbackend.reading_book.dto;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.springframework.lang.Nullable;


public record ReadingBookUpdateRequest(
    @Nullable
    Integer currentPage,
    @Nullable
    ReadingStatus status,
    @Nullable
    @Valid
    ReadingBookDurationRange durationRange) {
}
