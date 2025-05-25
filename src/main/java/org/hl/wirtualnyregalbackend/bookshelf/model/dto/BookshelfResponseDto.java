package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.book.model.dto.BookResponseDto;

import java.util.List;

public record BookshelfResponseDto(
    Long id,
    @JsonUnwrapped
    BookshelfMutationDto bookshelfMutationDto,
    List<BookResponseDto> books
) {
}
