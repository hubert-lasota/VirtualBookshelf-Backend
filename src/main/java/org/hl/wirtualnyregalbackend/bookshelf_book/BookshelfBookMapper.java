package org.hl.wirtualnyregalbackend.bookshelf_book;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfHeaderResponseDto;
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
                                                                      Long totalNotes,
                                                                      Locale locale) {
        BookResponseDto book = BookMapper.toBookResponseDto(bookshelfBook.getBook(), stats, locale);

        Bookshelf bookshelf = bookshelfBook.getBookshelf();
        BookshelfHeaderResponseDto bookshelfDto = new BookshelfHeaderResponseDto(bookshelf.getId(), bookshelf.getName());

        return new BookshelfBookResponseDto(
            bookshelfBook.getCurrentPage(),
            bookshelfBook.getStatus(),
            bookshelfBook.getRangeDate(),
            bookshelfBook.getId(),
            bookshelfBook.getProgressPercentage(),
            book,
            bookshelfDto,
            totalNotes,
            bookshelfBook.getCreatedAt(),
            bookshelfBook.getUpdatedAt()
        );
    }


}
