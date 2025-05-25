package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.model.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.List;
import java.util.Locale;

class BookshelfMapper {

    private BookshelfMapper() {
    }

    public static Bookshelf toBookshelf(BookshelfMutationDto bookshelfMutationDto, User user) {
        return new Bookshelf(
            bookshelfMutationDto.name(),
            bookshelfMutationDto.type(),
            bookshelfMutationDto.description(),
            user
        );
    }

    public static BookshelfResponseDto toBookshelfResponseDto(Bookshelf bookshelf,
                                                              Locale locale) {
        BookshelfMutationDto bookshelfMutationDto = toBookshelfMutationDto(bookshelf);

        List<BookResponseDto> books = bookshelf.getBooks()
            .stream()
            .map(book -> BookMapper.toBookResponseDto(book, locale))
            .toList();

        return new BookshelfResponseDto(bookshelf.getId(), bookshelfMutationDto, books);
    }

    public static BookshelfMutationDto toBookshelfMutationDto(Bookshelf bookshelf) {
        return new BookshelfMutationDto(bookshelf.getName(), bookshelf.getType(), bookshelf.getDescription());
    }

}
