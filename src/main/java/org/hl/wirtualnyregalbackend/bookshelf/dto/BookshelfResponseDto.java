package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Instant;

public record BookshelfResponseDto(
    Long id,
    @JsonUnwrapped
    BookshelfMutationDto dto,
    Long totalBooks,
    Instant createdAt,
    Instant updatedAt
) {
}

