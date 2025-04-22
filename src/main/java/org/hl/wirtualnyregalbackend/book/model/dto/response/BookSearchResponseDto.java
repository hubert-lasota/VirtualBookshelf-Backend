package org.hl.wirtualnyregalbackend.book.model.dto.response;

import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;

import java.util.Collection;

public record BookSearchResponseDto(
    Long id,
    String title,
    Collection<AuthorDto> authors,
    String coverUrl
) {
}
