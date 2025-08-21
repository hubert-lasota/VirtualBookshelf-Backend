package org.hl.wirtualnyregalbackend.reading_book.dto;

import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.common.model.ReadingDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;


public record ReadingBookResponse(
    Long id,
    Float progressPercentage,
    Integer currentPage,
    Long totalNotes,
    ReadingDurationRange durationRange,
    ReadingStatus status,
    BookResponse book,
    BookshelfSummaryResponse bookshelf
) {
}
