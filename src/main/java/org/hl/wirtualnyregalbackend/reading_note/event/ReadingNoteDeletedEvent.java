package org.hl.wirtualnyregalbackend.reading_note.event;

import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;

public record ReadingNoteDeletedEvent(Long readingBookId) {

    public static ReadingNoteDeletedEvent from(ReadingNote readingNote) {
        return new ReadingNoteDeletedEvent(readingNote.getReadingBook().getId());
    }

}
