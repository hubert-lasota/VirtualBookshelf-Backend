package org.hl.wirtualnyregalbackend.reading_note;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteMutationDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponseDto;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;

class ReadingNoteMapper {

    private ReadingNoteMapper() {
    }


    public static ReadingNoteResponseDto toReadingNoteResponseDto(ReadingNote note) {
        ReadingNoteMutationDto dto = new ReadingNoteMutationDto(
            note.getTitle(),
            note.getContent(),
            note.getPageFrom(),
            note.getPageTo()
        );

        return new ReadingNoteResponseDto(
            note.getId(),
            dto,
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }

    public static ReadingNote toReadingNote(ReadingNoteMutationDto noteDto, ReadingBook readingBook) {
        return new ReadingNote(
            noteDto.getTitle(),
            noteDto.getContent(),
            noteDto.getPageFrom(),
            noteDto.getPageTo(),
            readingBook
        );
    }

}
