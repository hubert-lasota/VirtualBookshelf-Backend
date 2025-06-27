package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfCreateDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf_book.BookshelfBookMapper;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.util.List;
import java.util.Locale;

class BookshelfMapper {

    private BookshelfMapper() {
    }

    public static Bookshelf toBookshelf(BookshelfCreateDto bookshelfDto, User user, List<BookshelfBook> bookshelfBooks) {
        return new Bookshelf(
            bookshelfDto.getName(),
            bookshelfDto.getType(),
            bookshelfDto.getDescription(),
            user,
            bookshelfBooks
        );
    }

    public static BookshelfResponseDto toBookshelfResponseDto(Bookshelf bookshelf,
                                                              List<ReviewStats> reviewStats,
                                                              Locale locale) {
        List<BookshelfBookResponseDto> books = bookshelf
            .getBookshelfBooks()
            .stream()
            .map(bookshelfBook -> {
                ReviewStats stats = reviewStats
                    .stream()
                    .filter(s -> bookshelfBook.getBook().getId().equals(s.entityId()))
                    .findFirst()
                    .orElseThrow();

                return BookshelfBookMapper.toBookshelfBookResponseDto(bookshelfBook, stats, locale);
            })
            .toList();

        return new BookshelfResponseDto(
            bookshelf.getName(),
            bookshelf.getType(),
            bookshelf.getDescription(),
            bookshelf.getId(),
            books,
            bookshelf.getCreatedAt(),
            bookshelf.getUpdatedAt()
        );
    }


}
