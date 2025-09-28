package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.dto.NoteInSessionDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionRequest;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;

import java.util.ArrayList;
import java.util.List;

class ReadingSessionMapper {

    private ReadingSessionMapper() {
    }

    public static ReadingSession toReadingSession(ReadingSessionRequest session, ReadingBook book) {
        return new ReadingSession(
            session.title(),
            session.pageRange(),
            session.durationRange(),
            book,
            new ArrayList<>()
        );
    }

    public static ReadingSessionResponse toReadingSessionResponse(ReadingSession session) {
        List<NoteInSessionDto> notes = session
            .getNotes()
            .stream()
            .map(note -> new NoteInSessionDto(note.getId(), note.getTitle(), note.getContent()))
            .toList();

        return new ReadingSessionResponse(
            session.getId(),
            session.getTitle(),
            session.getPageRange(),
            session.getDurationRange(),
            notes
        );
    }

}
