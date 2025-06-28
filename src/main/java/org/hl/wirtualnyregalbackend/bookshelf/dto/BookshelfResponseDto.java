package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;

import java.time.Instant;
import java.util.List;

public record BookshelfResponseDto(
    Long id,
    @JsonUnwrapped
    BookshelfMutationDto dto,
    List<BookshelfBookResponseDto> books,
    Instant createdAt,
    Instant updatedAt
) {
}

