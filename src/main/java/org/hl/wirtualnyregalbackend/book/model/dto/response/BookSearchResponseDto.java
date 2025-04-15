package org.hl.wirtualnyregalbackend.book.model.dto.response;

import org.hl.wirtualnyregalbackend.author.model.dto.AuthorResponse;

import java.util.Collection;

public record BookSearchResponseDto(
    Long id,
    String title,
    Collection<AuthorResponse> authors,
    String coverUrl
) { }
