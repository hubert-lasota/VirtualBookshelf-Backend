package org.hl.wirtualnyregalbackend.reading_note;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteRequest;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.SessionInNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_session.dto.NoteInSessionDto;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.springframework.lang.Nullable;

public class ReadingNoteMapper {

    private ReadingNoteMapper() {
    }


    public static ReadingNoteResponse toReadingNoteResponse(ReadingNote note) {
        ReadingSession rs = note.getReadingSession();
        SessionInNoteResponse session = rs != null ? new SessionInNoteResponse(rs.getId(), rs.getTitle()) : null;
        return new ReadingNoteResponse(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getPageRange(),
            session,
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }

    public static ReadingNote toReadingNote(ReadingNoteRequest note,
                                            ReadingBook readingBook,
                                            @Nullable ReadingSession readingSession) {
        return new ReadingNote(
            note.title(),
            note.content(),
            note.pageRange(),
            readingBook,
            readingSession
        );
    }

    public static ReadingNote toReadingNote(ReadingSession session, NoteInSessionDto note) {
        return new ReadingNote(
            note.title(),
            note.content(),
            session.getPageRange(),
            session.getReadingBook(),
            session
        );
    }

}
