package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.model.dto.BookEditionDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.request.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.response.BookEditionInBookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.response.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class BookshelfMapper {

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

        List<BookEditionInBookshelfResponseDto> editions = bookshelf.getBookEditions()
            .stream()
            .map(edition -> {
                Set<Author> authorEntities = edition.getBook().getAuthors();
                List<AuthorDto> authorDtos = authorEntities.stream()
                    .map(AuthorMapper::toAuthorDto)
                    .toList();

                BookEditionDto editionDto = BookMapper.toBookEditionDto(edition, locale);
                return new BookEditionInBookshelfResponseDto(editionDto, authorDtos);
            })
            .toList();

        return new BookshelfResponseDto(bookshelf.getId(), bookshelfMutationDto, editions);
    }

    public static BookshelfMutationDto toBookshelfMutationDto(Bookshelf bookshelf) {
        return new BookshelfMutationDto(bookshelf.getName(), bookshelf.getType(), bookshelf.getDescription());
    }

}
