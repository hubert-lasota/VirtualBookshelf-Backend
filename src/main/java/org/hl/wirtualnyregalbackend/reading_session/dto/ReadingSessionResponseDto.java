package org.hl.wirtualnyregalbackend.reading_session.dto;


import java.time.Instant;

public record ReadingSessionResponseDto(
    Long id,
    Integer pageFrom,
    Integer pageTo,
    Instant startedReadingAt,
    Instant finishedReadingAt
) {
}
