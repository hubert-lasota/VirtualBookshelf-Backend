package org.hl.wirtualnyregalbackend.bookshelf.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

import java.util.List;

@Getter
public class BookshelfBookCreateDto extends BaseBookshelfBookDto {

    @NotNull(groups = CreateGroup.class)
    @Valid
    protected final BookWithIdDto book;

    public BookshelfBookCreateDto(Integer currentPage,
                                  BookReadingStatus status,
                                  RangeDate rangeDate,
                                  List<BookshelfBookNoteDto> notes,
                                  BookWithIdDto book) {
        super(currentPage, status, rangeDate, notes);
        this.book = book;
    }

}
