package org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto;

import org.hl.wirtualnyregalbackend.application.bookshelf.BookshelfType;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookResponse;

import java.time.Instant;
import java.util.Collection;

public record BookshelfResponse(Long id,
                                String name,
                                BookshelfType type,
                                String description,
                                Collection<BookResponse> books,
                                Instant createdAtTimestamp,
                                Instant updatedAtTimestamp) {

}
