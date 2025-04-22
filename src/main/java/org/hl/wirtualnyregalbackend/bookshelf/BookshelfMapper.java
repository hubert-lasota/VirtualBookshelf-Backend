package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfHeaderResponse;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.List;
import java.util.Locale;

public class BookshelfMapper {

    private BookshelfMapper() {
    }

    public static Bookshelf toBookshelf(BookshelfDto bookshelfDto, User user) {
        return new Bookshelf(
            bookshelfDto.name(),
            bookshelfDto.type(),
            bookshelfDto.description(),
            user
        );
    }

    public static BookshelfResponseDto toBookshelfResponseDto(Bookshelf bookshelf) {
        BookshelfDto bookshelfDto = new BookshelfDto(bookshelf.getName(), bookshelf.getType(), bookshelf.getDescription());
        return new BookshelfResponseDto(bookshelf.getId(), bookshelfDto);
    }

    public static BookshelfHeaderResponse toBookshelfHeaderResponse(Bookshelf bookshelf) {
        return new BookshelfHeaderResponse(
            bookshelf.getId(),
            bookshelf.getName(),
            bookshelf.getType()
        );
    }

    public static BookshelfResponse toBookshelfResponse(Bookshelf bookshelf, Locale locale) {
        BookshelfHeaderResponse header = toBookshelfHeaderResponse(bookshelf);

        List<BookResponseDto> books = bookshelf.getBooks()
            .stream()
            .map(book -> BookMapper.toBookResponseDto(book, locale))
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
