package org.hl.wirtualnyregalbackend.infrastructure.book.dto;

import org.hl.wirtualnyregalbackend.application.book.BookIsbn;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Collection;

public record BookResponse(String id,
                           String isbn,
                           String title,
                           Collection<AuthorResponse> authors,
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
                           String coverUrl) {

    public BookResponse {
        if(id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }

        isbn = new BookIsbn(isbn).getStandardizedIsbn();

        if(authors == null || authors.isEmpty()) {
            throw new IllegalArgumentException("Authors cannot be null or empty");
        }
    }

}
