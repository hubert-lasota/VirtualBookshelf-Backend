package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.dto.*;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfBookNote;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.util.List;
import java.util.Locale;
import java.util.Set;

class BookshelfMapper {

    private BookshelfMapper() {
    }

    public static Bookshelf toBookshelf(BookshelfCreateDto bookshelfDto, User user, Set<BookshelfBook> bookshelfBooks) {
        return new Bookshelf(
            bookshelfDto.getName(),
            bookshelfDto.getType(),
            bookshelfDto.getDescription(),
            user,
            bookshelfBooks
        );
    }

    public static BookshelfResponseDto toBookshelfResponseDto(Bookshelf bookshelf,
                                                              Locale locale) {
        List<BookshelfBookResponseDto> books = bookshelf.getBookshelfBooks()
            .stream()
            .map(bookshelfBook -> BookshelfMapper.toBookshelfBookResponseDto(bookshelfBook, locale))
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


    public static BookshelfBook toBookshelfBook(BookshelfBookCreateDto bookshelfBookDto, Book book) {
        List<BookshelfBookNote> notes = bookshelfBookDto.getNotes()
            .stream()
            .map(BookshelfMapper::toBookshelfBookNote)
            .toList();

        return new BookshelfBook(
            bookshelfBookDto.getCurrentPage(),
            book,
            bookshelfBookDto.getRangeDate(),
            bookshelfBookDto.getStatus(),
            notes
        );
    }

    public static BookshelfBookResponseDto toBookshelfBookResponseDto(BookshelfBook bookshelfBook, Locale locale) {
        BookResponseDto book = BookMapper.toBookResponseDto(bookshelfBook.getBook(), locale);
        List<BookshelfBookNoteDto> notes = bookshelfBook.getNotes()
            .stream()
            .map(BookshelfMapper::toBookshelfBookNoteDto)
            .toList();

        return new BookshelfBookResponseDto(
            bookshelfBook.getCurrentPage(),
            bookshelfBook.getStatus(),
            bookshelfBook.getRangeDate(),
            notes,
            bookshelfBook.getId(),
            bookshelfBook.getProgressPercentage(),
            book,
            bookshelfBook.getCreatedAt(),
            bookshelfBook.getUpdatedAt()
        );
    }

    public static BookshelfBookNoteDto toBookshelfBookNoteDto(BookshelfBookNote note) {
        return new BookshelfBookNoteDto(
            note.getContent(),
            note.getStartPage(),
            note.getEndPage()
        );
    }

    public static BookshelfBookNote toBookshelfBookNote(BookshelfBookNoteDto noteDto) {
        return new BookshelfBookNote(
            noteDto.content(),
            noteDto.startPage(),
            noteDto.endPage()
        );
    }

}
