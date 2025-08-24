package org.hl.wirtualnyregalbackend.reading_session.event;

import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;

public record ReadingSessionDeletedEvent(ReadingSession readingSession) {
}
