package org.hl.wirtualnyregalbackend.reading_book.event;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.springframework.lang.Nullable;

public record ReadingBookChangedStatusEvent(
    Long readingBookId,
    ReadingStatus status,
    ReadingStatus previousStatus,
    @Nullable
    ReadingBookDurationRange durationRange,
    Long userId
) {

    public static ReadingBookChangedStatusEvent fromStatusChange(ReadingBook readingBook, ReadingStatus previousStatus) {
        return new ReadingBookChangedStatusEvent(
            readingBook.getId(),
            readingBook.getStatus(),
            previousStatus,
            readingBook.getDurationRange(),
            readingBook.getBookshelf().getUser().getId()
        );
    }

}
