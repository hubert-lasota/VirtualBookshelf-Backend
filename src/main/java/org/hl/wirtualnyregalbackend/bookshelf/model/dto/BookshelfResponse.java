package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponse;

import java.time.Instant;
import java.util.Collection;

public record BookshelfResponse(BookshelfHeaderResponse headerResponse,
                                String description,
                                Collection<BookResponse> books,
                                Instant createdAtTimestamp,
                                Instant updatedAtTimestamp) {

}
