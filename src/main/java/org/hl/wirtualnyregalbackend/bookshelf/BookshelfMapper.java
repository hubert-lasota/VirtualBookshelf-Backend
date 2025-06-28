package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.util.List;

class BookshelfMapper {

    private BookshelfMapper() {
    }

    public static Bookshelf toBookshelf(BookshelfMutationDto bookshelfDto, User user) {
        return new Bookshelf(
            bookshelfDto.name(),
            bookshelfDto.type(),
            bookshelfDto.description(),
            user
        );
    }

    public static BookshelfResponseDto toBookshelfResponseDto(Bookshelf bookshelf,
                                                              List<BookshelfBookResponseDto> bookshelfBooks) {
        BookshelfMutationDto bookshelfDto = new BookshelfMutationDto(
            bookshelf.getName(),
            bookshelf.getType(),
            bookshelf.getDescription()
        );

        return new BookshelfResponseDto(
            bookshelf.getId(),
            bookshelfDto,
            bookshelfBooks,
            bookshelf.getCreatedAt(),
            bookshelf.getUpdatedAt()
        );
    }


}
