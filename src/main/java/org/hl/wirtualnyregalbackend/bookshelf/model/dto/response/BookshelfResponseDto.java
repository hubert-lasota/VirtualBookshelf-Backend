package org.hl.wirtualnyregalbackend.bookshelf.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.request.BookshelfMutationDto;

import java.util.List;

public record BookshelfResponseDto(
    Long id,
    @JsonUnwrapped
    BookshelfMutationDto bookshelfMutationDto,
    List<BookEditionInBookshelfResponseDto> editions
) {
}
