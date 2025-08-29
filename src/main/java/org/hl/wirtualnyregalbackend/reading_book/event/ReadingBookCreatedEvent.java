package org.hl.wirtualnyregalbackend.reading_book.event;

import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;

public record ReadingBookCreatedEvent(
    Long readingBookId,
    ReadingStatus status,
    Long bookshelfId,
    Long bookId,
    Long userId
) {

    public static ReadingBookCreatedEvent from(ReadingBook readingBook) {
        Book book = readingBook.getBook();
        Bookshelf bookshelf = readingBook.getBookshelf();
        return new ReadingBookCreatedEvent(
            readingBook.getId(),
            readingBook.getStatus(),
            bookshelf.getId(),
            book.getId(),
            bookshelf.getUser().getId()
        );
    }

}
