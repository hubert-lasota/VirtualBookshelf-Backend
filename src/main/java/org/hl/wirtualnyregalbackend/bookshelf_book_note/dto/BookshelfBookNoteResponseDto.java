package org.hl.wirtualnyregalbackend.bookshelf_book_note.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Instant;

public record BookshelfBookNoteResponseDto(
    Long id,
    @JsonUnwrapped
    BookshelfBookNoteMutationDto dto,
    Instant createdAt,
    Instant updatedAt
) {
}
