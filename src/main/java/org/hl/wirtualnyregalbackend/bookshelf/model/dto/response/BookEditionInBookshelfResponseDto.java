package org.hl.wirtualnyregalbackend.bookshelf.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookEditionDto;

import java.util.List;

public record BookEditionInBookshelfResponseDto(
    @JsonUnwrapped
    BookEditionDto bookEditionDto,
    List<AuthorDto> authors
) {
}
