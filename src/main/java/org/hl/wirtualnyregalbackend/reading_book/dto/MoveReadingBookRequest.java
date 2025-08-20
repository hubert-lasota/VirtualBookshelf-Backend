package org.hl.wirtualnyregalbackend.reading_book.dto;

import jakarta.validation.constraints.NotNull;

public record MoveReadingBookRequest(@NotNull Long bookshelfId) {
}
