package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import jakarta.validation.constraints.NotNull;

public record MoveBookshelfBookDto(@NotNull Long bookshelfId) {
}
