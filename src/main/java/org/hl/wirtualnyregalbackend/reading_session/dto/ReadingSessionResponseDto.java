package org.hl.wirtualnyregalbackend.reading_session.dto;


import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Instant;

public record ReadingSessionResponseDto(
    Long id,
    @JsonUnwrapped
    ReadingSessionUpdateDto dtoToUnwrap,
    Instant createdAt,
    Instant updatedAt
) {
}
