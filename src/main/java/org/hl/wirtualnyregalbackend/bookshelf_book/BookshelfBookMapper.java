package org.hl.wirtualnyregalbackend.bookshelf_book;

import org.hl.wirtualnyregalbackend.book.BookMapper;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBookNote;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookNoteDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BookshelfBookMapper {

    private BookshelfBookMapper() {
    }


    public static BookshelfBook toBookshelfBook(BookshelfBookMutationDto bookshelfBookDto, Book book) {
        List<BookshelfBookNoteDto> noteDtos = bookshelfBookDto.getNotes() != null ? bookshelfBookDto.getNotes() : Collections.emptyList();
        List<BookshelfBookNote> notes = noteDtos
            .stream()
            .map(BookshelfBookMapper::toBookshelfBookNote)
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
            .map(BookshelfBookMapper::toBookshelfBookNoteDto)
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
            note.getTitle(),
            note.getContent(),
            note.getStartPage(),
            note.getEndPage()
        );
    }

    public static BookshelfBookNote toBookshelfBookNote(BookshelfBookNoteDto noteDto) {
        return new BookshelfBookNote(
            noteDto.title(),
            noteDto.content(),
            noteDto.startPage(),
            noteDto.endPage()
        );
    }


}
