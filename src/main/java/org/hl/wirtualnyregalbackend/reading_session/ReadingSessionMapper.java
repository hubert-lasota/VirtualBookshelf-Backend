package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponseDto;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;

class ReadingSessionMapper {

    private ReadingSessionMapper() {
    }

    public static ReadingSession toReadingSession(ReadingSessionCreateDto sessionDto, ReadingBook book) {
        return new ReadingSession(
            sessionDto.getPageFrom(),
            sessionDto.getPageTo(),
            sessionDto.getStartedReadingAt(),
            sessionDto.getFinishedReadingAt(),
            sessionDto.getDescription(),
            book
        );
    }

    public static ReadingSessionResponseDto toReadingSessionResponseDto(ReadingSession session) {
        BookResponseDto book = BookMapper.toBookResponseDto(session.getReadingBook().getBook());
        return new ReadingSessionResponseDto(
            session.getId(),
            session.getPageFrom(),
            session.getPageTo(),
            session.getDescription(),
            session.getStartedReadingAt(),
            session.getFinishedReadingAt(),
            book
        );
    }

}
