package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record BookshelfResponseDto(
    Long id,
    @JsonUnwrapped
    BookshelfMutationDto bookshelfMutationDto,
    List<BookshelfBookResponseDto> books
) {
}
