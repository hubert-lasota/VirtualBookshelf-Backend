package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ReadingBookEventListener {

    private final ReadingBookService readingBookService;

    @EventListener
    @Async
    public void handleReadPagesEvent(ReadPagesEvent event) {
        ReadingSession session = event.readingSession();
        ReadingBook readingBook = session.getReadingBook();
        Book book = readingBook.getBook();
        Integer pageTo = session.getPageTo();
        if (book.getPageCount().equals(pageTo)) {
            readingBookService.changeReadingBookStatus(readingBook.getId(), ReadingStatus.READ);
        }
    }

}
