package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
@Transactional
class BookshelfEventListener {

    private final BookshelfService bookshelfService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingBookCreatedEvent(ReadingBookCreatedEvent event) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(event.bookshelfId());
        bookshelf.incrementTotalBooks();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingBookDeletedEvent(ReadingBookDeletedEvent event) {
        Bookshelf bookshelf = bookshelfService.findBookshelfById(event.bookshelfId());
        bookshelf.decrementTotalBooks();
    }

}
