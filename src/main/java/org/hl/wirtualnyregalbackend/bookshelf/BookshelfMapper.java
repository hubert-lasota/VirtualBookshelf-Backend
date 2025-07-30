package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.auth.entity.User;

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

    public static BookshelfResponseDto toBookshelfResponseDto(Bookshelf bookshelf, Long totalBooks) {
        BookshelfMutationDto bookshelfDto = new BookshelfMutationDto(
            bookshelf.getName(),
            bookshelf.getType(),
            bookshelf.getDescription()
        );

        return new BookshelfResponseDto(
            bookshelf.getId(),
            bookshelfDto,
            totalBooks,
            bookshelf.getCreatedAt(),
            bookshelf.getUpdatedAt()
        );
    }


}
