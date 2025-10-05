package org.hl.wirtualnyregalbackend.reading_book.dto;

import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;


public record ReadingBookResponse(
    Long id,
    float progressPercentage,
    int currentPage,
    long totalNotes,
    long totalSessions,
    ReadingBookDurationRange durationRange,
    ReadingStatus status,
    BookResponse book,
    BookshelfSummaryResponse bookshelf
) {
}
