package org.hl.wirtualnyregalbackend.reading_book.event;

import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

public record ReadingBookDeletedEvent(Long bookshelfId, Long bookId, Long userId) {

    public static ReadingBookDeletedEvent from(ReadingBook readingBook) {
        Bookshelf b = readingBook.getBookshelf();
        return new ReadingBookDeletedEvent(
            b.getId(),
            readingBook.getBook().getId(),
            b.getUser().getId()
        );
    }

}
