package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;

class BookshelfMapper {

    private BookshelfMapper() {
    }

    public static Bookshelf toBookshelf(BookshelfRequest bookshelfRequest, User user) {
        return new Bookshelf(
            bookshelfRequest.name(),
            bookshelfRequest.type(),
            bookshelfRequest.description(),
            user
        );
    }

    public static BookshelfResponse toBookshelfResponse(Bookshelf bookshelf, Long totalBooks) {
        return new BookshelfResponse(
            bookshelf.getId(),
            bookshelf.getName(),
            bookshelf.getType(),
            bookshelf.getDescription(),
            totalBooks
        );
    }


}
