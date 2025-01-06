package org.hl.wirtualnyregalbackend.application.bookshelf;

import org.hl.wirtualnyregalbackend.application.book.BookMapper;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.util.List;

public class BookshelfMapper {

    private BookshelfMapper() { }

    public static Bookshelf toBookshelf(BookshelfRequest bookshelfRequest, User user) {
        return new Bookshelf(
                bookshelfRequest.name(),
                bookshelfRequest.type(),
                bookshelfRequest.description(),
                user
        );
    }

    public static BookshelfResponse toBookshelfResponse(Bookshelf bookshelf) {
        List<BookResponse> books = bookshelf.getBooks()
                .stream()
                .map(BookMapper::toBookResponse)
                .toList();

        return new BookshelfResponse(
                bookshelf.getId(),
                bookshelf.getName(),
                bookshelf.getType(),
                bookshelf.getDescription(),
                books,
                bookshelf.getCreatedAt(),
                bookshelf.getUpdatedAt()
        );
    }

}
