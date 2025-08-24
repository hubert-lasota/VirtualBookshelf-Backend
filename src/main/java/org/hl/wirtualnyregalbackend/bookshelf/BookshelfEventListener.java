package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookshelfEventListener {

    private final BookshelfRepository bookshelfRepository;

    @EventListener
    public void handleReadingBookCreatedEvent(ReadingBookCreatedEvent event) {
        Bookshelf bookshelf = event.readingBook().getBookshelf();
        bookshelf.incrementTotalBooks();
        bookshelfRepository.save(bookshelf);
    }

    @EventListener
    public void handleReadingBookDeletedEvent(ReadingBookDeletedEvent event) {
        Bookshelf bookshelf = event.readingBook().getBookshelf();
        bookshelf.decrementTotalBooks();
        bookshelfRepository.save(bookshelf);
    }

}
