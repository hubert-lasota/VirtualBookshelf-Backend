package org.hl.wirtualnyregalbackend.infrastructure.book.dto.response;

import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Collection;


public record BookResponse(String id,
                           String isbn,
                           String title,
                           Collection<AuthorResponse> authors,
                           @Nullable
                           Collection<String> publishers,
                           @Nullable
                           Collection<BookGenreResponse> genres,
                           @Nullable
                           String description,
                           @Nullable
                           Instant publishedAtTimestamp,
                           @Nullable
                           Integer publishedYear,
                           @Nullable
                           String language,
                           @Nullable
                           Integer numberOfPages,
                           @Nullable
                           String coverUrl) { }
