package org.hl.wirtualnyregalbackend.reading_session.event;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;

public record ReadingSessionCreatedEvent(Long readingBookId, Long bookId, Long userId) {

    public static ReadingSessionCreatedEvent from(ReadingSession session) {
        ReadingBook rb = session.getReadingBook();
        return new ReadingSessionCreatedEvent(rb.getId(), rb.getBook().getId(), rb.getBookshelf().getUser().getId());
    }

}
