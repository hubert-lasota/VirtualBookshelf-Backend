package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class BookshelfEventListener {

    private final BookshelfService bookshelfService;
    private final BookshelfRepository bookshelfRepository;

    @EventListener
    public void handleReadingBookCreatedEvent(ReadingBookCreatedEvent event) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(event.bookshelfId());
        bookshelf.incrementTotalBooks();
        bookshelfRepository.save(bookshelf);
    }

    @EventListener
    public void handleReadingBookDeletedEvent(ReadingBookDeletedEvent event) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(event.bookshelfId());
        bookshelf.decrementTotalBooks();
        bookshelfRepository.save(bookshelf);
    }

}
