package org.hl.wirtualnyregalbackend.reading_note;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponseDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteUpdateDto;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;

class ReadingNoteMapper {

    private ReadingNoteMapper() {
    }


    public static ReadingNoteResponseDto toReadingNoteResponseDto(ReadingNote note) {
        return new ReadingNoteResponseDto(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getPageFrom(),
            note.getPageTo(),
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }

    public static ReadingNote toReadingNote(ReadingNoteUpdateDto noteDto, ReadingBook readingBook) {
        return new ReadingNote(
            noteDto.getTitle(),
            noteDto.getContent(),
            noteDto.getPageFrom(),
            noteDto.getPageTo(),
            readingBook
        );
    }

}
