package org.hl.wirtualnyregalbackend.reading_book.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;


public record ReadingBookCreateRequest(
    @NotNull
    Long bookshelfId,
    @NotNull
    @Valid
    BookWithIdDto book,
    ReadingStatus status,
    ReadingBookDurationRange durationRange
) {
}
