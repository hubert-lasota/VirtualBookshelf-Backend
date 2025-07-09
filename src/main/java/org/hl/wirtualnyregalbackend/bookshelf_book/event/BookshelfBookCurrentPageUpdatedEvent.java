package org.hl.wirtualnyregalbackend.bookshelf_book.event;

import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;

public record BookshelfBookCurrentPageUpdatedEvent(
    BookshelfBook bookshelfBook,
    Integer oldPage,
    Integer newPage
) {
}
