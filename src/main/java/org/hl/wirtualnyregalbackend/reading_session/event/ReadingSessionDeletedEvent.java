package org.hl.wirtualnyregalbackend.reading_session.event;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;

public record ReadingSessionDeletedEvent(Long readingBookId) {

    public static ReadingSessionDeletedEvent from(ReadingSession session) {
        return new ReadingSessionDeletedEvent(session.getReadingBook().getId());
    }

}
