package org.hl.wirtualnyregalbackend.bookshelf.dto;

import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;


public record BookshelfResponseDto(
    Long id,
    String name,
    BookshelfType type,
    String description,
    Long totalBooks
) {
}

