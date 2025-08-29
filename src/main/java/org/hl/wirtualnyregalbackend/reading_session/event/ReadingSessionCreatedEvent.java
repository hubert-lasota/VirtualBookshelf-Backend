package org.hl.wirtualnyregalbackend.reading_session.event;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;

public record ReadingSessionCreatedEvent(Long readingBookId) {

    public static ReadingSessionCreatedEvent from(ReadingSession session) {
        return new ReadingSessionCreatedEvent(session.getReadingBook().getId());
    }

}
