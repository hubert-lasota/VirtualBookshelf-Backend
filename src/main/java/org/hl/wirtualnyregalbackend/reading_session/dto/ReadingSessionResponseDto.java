package org.hl.wirtualnyregalbackend.reading_session.dto;


import java.time.Instant;

public class ReadingSessionResponseDto extends ReadingSessionMutationDto {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;

    public ReadingSessionResponseDto(Integer pageFrom,
                                     Integer pageTo,
                                     Instant startedReadingAt,
                                     Instant finishedReadingAt,
                                     Long id,
                                     Instant createdAt,
                                     Instant updatedAt) {
        super(pageFrom, pageTo, startedReadingAt, finishedReadingAt);
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
