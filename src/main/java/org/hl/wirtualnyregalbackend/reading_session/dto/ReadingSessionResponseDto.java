package org.hl.wirtualnyregalbackend.reading_session.dto;


import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;

import java.time.Instant;

public record ReadingSessionResponseDto(
    Long id,
    Integer pageFrom,
    Integer pageTo,
    String description,
    Instant startedReadingAt,
    Instant finishedReadingAt,
    BookResponseDto book
) {
}
