package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record BookshelfResponseDto(
    Long id,
    @JsonUnwrapped
    BookshelfDto bookshelfDto
) {
}
