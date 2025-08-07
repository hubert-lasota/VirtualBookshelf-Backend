package org.hl.wirtualnyregalbackend.reading_book;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.hl.wirtualnyregalbackend.reading_book.dto.BookshelfHeaderResponseDto;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookResponseDto;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookUpdateDto;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

import java.util.Locale;

public class ReadingBookMapper {

    private ReadingBookMapper() {
    }


    public static ReadingBook toReadingBook(ReadingBookUpdateDto readingBookDto,
                                            Bookshelf bookshelf,
                                            Book book) {
        return new ReadingBook(
            readingBookDto.getStatus(),
            readingBookDto.getStartedReadingAt(),
            readingBookDto.getFinishedReadingAt(),
            bookshelf,
            book
        );
    }

    public static ReadingBookResponseDto toReadingBookResponseDto(ReadingBook readingBook,
                                                                  ReviewStats stats,
                                                                  Long totalNotes,
                                                                  Integer currentPage,
                                                                  Float progressPercentage,
                                                                  Locale locale) {
        BookResponseDto book = BookMapper.toBookResponseDto(readingBook.getBook(), stats, locale);

        Bookshelf bookshelf = readingBook.getBookshelf();
        BookshelfHeaderResponseDto bookshelfDto = new BookshelfHeaderResponseDto(bookshelf.getId(), bookshelf.getName());

        return new ReadingBookResponseDto(
            readingBook.getId(),
            progressPercentage,
            currentPage,
            totalNotes,
            readingBook.getStartedReadingAt(),
            readingBook.getFinishedReadingAt(),
            readingBook.getStatus(),
            book,
            bookshelfDto
        );
    }


}
