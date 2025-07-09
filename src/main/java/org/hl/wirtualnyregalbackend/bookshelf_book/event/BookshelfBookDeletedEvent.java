package org.hl.wirtualnyregalbackend.bookshelf_book.event;

import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;

public record BookshelfBookDeletedEvent(BookshelfBook bookshelfBook) {
}
