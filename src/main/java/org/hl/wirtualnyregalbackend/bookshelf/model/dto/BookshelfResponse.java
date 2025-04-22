package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import java.time.Instant;
import java.util.Collection;

public record BookshelfResponse(BookshelfHeaderResponse headerResponse,
                                String description,
                                Collection<BookResponseDto> books,
                                Instant createdAtTimestamp,
                                Instant updatedAtTimestamp) {

}
