package org.hl.wirtualnyregalbackend.reading_book.dto;

import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;

import java.time.Instant;


public record ReadingBookResponse(
    Long id,
    Float progressPercentage,
    Integer currentPage,
    Long totalNotes,
    Instant startedReadingAt,
    Instant finishedReadingAt,
    ReadingStatus status,
    BookResponse book,
    BookshelfSummaryResponse bookshelf
) {
}
