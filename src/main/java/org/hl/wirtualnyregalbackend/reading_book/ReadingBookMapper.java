package org.hl.wirtualnyregalbackend.reading_book;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.reading_book.dto.BookshelfSummaryResponse;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookCreateRequest;
import org.hl.wirtualnyregalbackend.reading_book.dto.ReadingBookResponse;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

import java.util.Locale;

public class ReadingBookMapper {

    private ReadingBookMapper() {
    }


    public static ReadingBook toReadingBook(ReadingBookCreateRequest bookRequest,
                                            Bookshelf bookshelf,
                                            Book book) {
        return new ReadingBook(
            bookRequest.status(),
            bookRequest.durationRange(),
            bookshelf,
            book
        );
    }

    public static ReadingBookResponse toReadingBookResponse(ReadingBook readingBook, Locale locale) {
        BookResponse book = BookMapper.toBookResponse(readingBook.getBook(), locale);

        Bookshelf bookshelf = readingBook.getBookshelf();
        BookshelfSummaryResponse bookshelfDto = new BookshelfSummaryResponse(bookshelf.getId(), bookshelf.getName());
        Integer currentPage = readingBook.getCurrentPage();
        return new ReadingBookResponse(
            readingBook.getId(),
            readingBook.calculateProgressPercentage(),
            currentPage,
            readingBook.getTotalNotes(),
            readingBook.getTotalSessions(),
            readingBook.getDurationRange(),
            readingBook.getStatus(),
            book,
            bookshelfDto
        );
    }

}
