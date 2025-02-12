package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponse;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfHeaderResponse;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.security.model.User;

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

    public static BookshelfHeaderResponse toBookshelfHeaderResponse(Bookshelf bookshelf) {
        return new BookshelfHeaderResponse(
                bookshelf.getId(),
                bookshelf.getName(),
                bookshelf.getType()
        );
    }

    public static BookshelfResponse toBookshelfResponse(Bookshelf bookshelf) {
        BookshelfHeaderResponse header = toBookshelfHeaderResponse(bookshelf);

        List<BookResponse> books = bookshelf.getBooks()
                .stream()
                .map(BookMapper::toBookResponse)
                .toList();

        return new BookshelfResponse(
                header,
                bookshelf.getDescription(),
                books,
                bookshelf.getCreatedAt(),
                bookshelf.getUpdatedAt()
        );
    }

}
