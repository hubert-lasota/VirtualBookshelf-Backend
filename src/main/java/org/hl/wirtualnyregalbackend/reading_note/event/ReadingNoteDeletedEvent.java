package org.hl.wirtualnyregalbackend.reading_note.event;

import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;

public record ReadingNoteDeletedEvent(ReadingNote readingNote) {
}
