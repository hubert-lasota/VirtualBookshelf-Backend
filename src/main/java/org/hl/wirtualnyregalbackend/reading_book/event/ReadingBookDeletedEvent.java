package org.hl.wirtualnyregalbackend.reading_book.event;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

public record ReadingBookDeletedEvent(Long bookshelfId) {

    public static ReadingBookDeletedEvent from(ReadingBook readingBook) {
        return new ReadingBookDeletedEvent(readingBook.getBookshelf().getId());
    }

}
