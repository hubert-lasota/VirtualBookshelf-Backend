package org.hl.wirtualnyregalbackend.book_reading.model.dto;

import java.time.Instant;

public record BookNoteResponseDto(
    String content,
    Integer startPage,
    Integer endPage,
    Instant createdAt,
    Instant updatedAt
) {
}
