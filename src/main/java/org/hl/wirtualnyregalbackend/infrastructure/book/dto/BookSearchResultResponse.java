package org.hl.wirtualnyregalbackend.infrastructure.book.dto;

import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;

import java.util.Collection;

public record BookSearchResultResponse(String id,
                                       String isbn,
                                       String title,
                                       Collection<AuthorResponse> authors,
                                       String coverUrl) { }
