package org.hl.wirtualnyregalbackend.reading_note.event;

import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;

public record ReadingNoteCreatedEvent(Long readingBookId) {

    public static ReadingNoteCreatedEvent from(ReadingNote readingNote) {
        return new ReadingNoteCreatedEvent(readingNote.getReadingBook().getId());
    }

}
