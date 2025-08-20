package org.hl.wirtualnyregalbackend.reading_note;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteUpdateRequest;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;

class ReadingNoteMapper {

    private ReadingNoteMapper() {
    }


    public static ReadingNoteResponse toReadingNoteResponse(ReadingNote note) {
        return new ReadingNoteResponse(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getPageRange(),
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }

    public static ReadingNote toReadingNote(ReadingNoteUpdateRequest noteDto, ReadingBook readingBook) {
        return new ReadingNote(
            noteDto.getTitle(),
            noteDto.getContent(),
            noteDto.getPageRange(),
            readingBook
        );
    }

}
