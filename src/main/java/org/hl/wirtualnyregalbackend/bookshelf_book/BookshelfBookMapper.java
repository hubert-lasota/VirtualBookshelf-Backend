package org.hl.wirtualnyregalbackend.bookshelf_book;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;

import java.util.Locale;

public class BookshelfBookMapper {

    private BookshelfBookMapper() {
    }


    public static BookshelfBook toBookshelfBook(BookshelfBookMutationDto bookshelfBookDto, Book book) {
        return new BookshelfBook(
            bookshelfBookDto.getCurrentPage(),
            book,
            bookshelfBookDto.getRangeDate(),
            bookshelfBookDto.getStatus()
        );
    }

    public static BookshelfBookResponseDto toBookshelfBookResponseDto(BookshelfBook bookshelfBook,
                                                                      ReviewStats stats,
                                                                      Locale locale) {
        BookResponseDto book = BookMapper.toBookResponseDto(bookshelfBook.getBook(), stats, locale);

        return new BookshelfBookResponseDto(
            bookshelfBook.getCurrentPage(),
            bookshelfBook.getStatus(),
            bookshelfBook.getRangeDate(),
            bookshelfBook.getId(),
            bookshelfBook.getProgressPercentage(),
            book,
            bookshelfBook.getCreatedAt(),
            bookshelfBook.getUpdatedAt()
        );
    }


}
