package org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto;

import org.hl.wirtualnyregalbackend.infrastructure.book.dto.response.BookResponse;

import java.time.Instant;
import java.util.Collection;

public record BookshelfResponse(BookshelfHeaderResponse headerResponse,
                                String description,
                                Collection<BookResponse> books,
                                Instant createdAtTimestamp,
                                Instant updatedAtTimestamp) {

}
