package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponseDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionUpdateDto;
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
            book
        );
    }

    public static ReadingSessionResponseDto toReadingSessionResponseDto(ReadingSession session) {
        ReadingSessionUpdateDto dto = new ReadingSessionUpdateDto(
            session.getPageFrom(),
            session.getPageTo(),
            session.getStartedReadingAt(),
            session.getFinishedReadingAt()
        );
        return new ReadingSessionResponseDto(
            session.getId(),
            dto,
            session.getCreatedAt(),
            session.getUpdatedAt()
        );
    }

}
