package org.hl.wirtualnyregalbackend.reading_note.dto;


import java.time.Instant;

public record ReadingNoteResponseDto(
    Long id,
    String title,
    String content,
    Integer pageFrom,
    Integer pageTo,
    Instant createdAt,
    Instant updatedAt
) {
}
