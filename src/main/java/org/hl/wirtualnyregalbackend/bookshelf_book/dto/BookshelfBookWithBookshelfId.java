package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookshelfBookWithBookshelfId extends BookshelfBookMutationDto {

    private Long bookshelfId;

    public BookshelfBookWithBookshelfId(Integer currentPage,
                                        BookReadingStatus status,
                                        RangeDate rangeDate,
                                        List<BookshelfBookNoteDto> notes,
                                        BookWithIdDto book,
                                        Long bookshelfId) {
        super(currentPage, status, rangeDate, notes, book);
        this.bookshelfId = bookshelfId;
    }

}
