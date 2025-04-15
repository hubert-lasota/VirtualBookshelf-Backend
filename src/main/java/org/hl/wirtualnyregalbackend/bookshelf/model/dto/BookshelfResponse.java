package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;

import java.time.Instant;
import java.util.Collection;

public record BookshelfResponse(BookshelfHeaderResponse headerResponse,
                                String description,
                                Collection<BookResponseDto> books,
                                Instant createdAtTimestamp,
                                Instant updatedAtTimestamp) {

}
