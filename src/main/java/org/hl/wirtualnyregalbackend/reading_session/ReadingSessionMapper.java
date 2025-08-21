package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateRequest;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;

class ReadingSessionMapper {

    private ReadingSessionMapper() {
    }

    public static ReadingSession toReadingSession(ReadingSessionCreateRequest sessionDto, ReadingBook book) {
        return new ReadingSession(
            sessionDto.getPageRange(),
            sessionDto.getDurationRange(),
            sessionDto.getDescription(),
            book
        );
    }

    public static ReadingSessionResponse toReadingSessionResponseDto(ReadingSession session) {
        BookResponse book = BookMapper.toBookResponse(session.getReadingBook().getBook());
        return new ReadingSessionResponse(
            session.getId(),
            session.getPageRange(),
            session.getDurationRange(),
            book
        );
    }

}
