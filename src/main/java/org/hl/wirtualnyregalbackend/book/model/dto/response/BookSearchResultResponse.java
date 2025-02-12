package org.hl.wirtualnyregalbackend.book.model.dto.response;

import org.hl.wirtualnyregalbackend.author.model.dto.AuthorResponse;

import java.util.Collection;

public record BookSearchResultResponse(String id,
                                       String isbn,
                                       String title,
                                       Collection<AuthorResponse> authors,
                                       String coverUrl) { }
