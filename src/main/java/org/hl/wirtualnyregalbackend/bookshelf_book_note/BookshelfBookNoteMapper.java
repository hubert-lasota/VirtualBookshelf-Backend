package org.hl.wirtualnyregalbackend.bookshelf_book_note;

import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.dto.BookshelfBookNoteMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.dto.BookshelfBookNoteResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.entity.BookshelfBookNote;

class BookshelfBookNoteMapper {

    private BookshelfBookNoteMapper() {
    }


    public static BookshelfBookNoteResponseDto toBookshelfBookNoteResponseDto(BookshelfBookNote note) {
        BookshelfBookNoteMutationDto dto = new BookshelfBookNoteMutationDto(
            note.getTitle(),
            note.getContent(),
            note.getStartPage(),
            note.getEndPage()
        );

        return new BookshelfBookNoteResponseDto(
            note.getId(),
            dto,
            note.getCreatedAt(),
            note.getUpdatedAt()
        );
    }

    public static BookshelfBookNote toBookshelfBookNote(BookshelfBookNoteMutationDto noteDto,
                                                        BookshelfBook bookshelfBook) {
        return new BookshelfBookNote(
            noteDto.title(),
            noteDto.content(),
            noteDto.startPage(),
            noteDto.endPage(),
            bookshelfBook
        );
    }

}
