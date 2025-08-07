package org.hl.wirtualnyregalbackend.reading_note.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Instant;

public record ReadingNoteResponseDto(
    Long id,
    @JsonUnwrapped
    ReadingNoteUpdateDto dtoToUnwrap,
    Instant createdAt,
    Instant updatedAt
) {
}
