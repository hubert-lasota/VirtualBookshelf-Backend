package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.model.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookshelfBookNote;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.*;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.List;
import java.util.Locale;

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
                                                              Locale locale) {
        BookshelfMutationDto bookshelfMutationDto = toBookshelfMutationDto(bookshelf);

        List<BookshelfBookResponseDto> books = bookshelf.getBookshelfBooks()
            .stream()
            .map(bookshelfBook -> BookshelfMapper.toBookshelfBookResponseDto(bookshelfBook, locale))
            .toList();

        return new BookshelfResponseDto(bookshelf.getId(), bookshelfMutationDto, books);
    }

    public static BookshelfMutationDto toBookshelfMutationDto(Bookshelf bookshelf) {
        return new BookshelfMutationDto(bookshelf.getName(), bookshelf.getType(), bookshelf.getDescription());
    }

    public static BookshelfBook toBookshelfBook(BookshelfBookMutationDto bookshelfBookDto,
                                                User user,
                                                Book book) {
        List<BookshelfBookNote> notes = bookshelfBookDto.notes()
            .stream()
            .map(BookshelfMapper::toBookshelfBookNote)
            .toList();

        return new BookshelfBook(
            bookshelfBookDto.currentPage(),
            user,
            book,
            bookshelfBookDto.rangeDate(),
            bookshelfBookDto.status(),
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
            bookshelfBook.getId(),
            bookshelfBook.getCreatedAt(),
            bookshelfBook.getUpdatedAt(),
            book,
            bookshelfBook.getCurrentPage(),
            bookshelfBook.getProgressPercentage(),
            bookshelfBook.getStatus(),
            bookshelfBook.getRangeDate(),
            notes
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
