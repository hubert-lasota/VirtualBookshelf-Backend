package org.hl.wirtualnyregalbackend.book.model.dto.response;

import java.time.Instant;

public record BookNoteResponseDto(
    String content,
    Integer startPage,
    Integer endPage,
    Instant createdAt,
    Instant updatedAt
) {
}
