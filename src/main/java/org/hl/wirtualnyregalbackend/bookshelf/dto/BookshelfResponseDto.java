package org.hl.wirtualnyregalbackend.bookshelf.dto;

import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;

import java.time.Instant;

public record BookshelfResponseDto(
    Long id,
    String name,
    BookshelfType type,
    String description,
    Long totalBooks,
    Instant createdAt,
    Instant updatedAt
) {
}

