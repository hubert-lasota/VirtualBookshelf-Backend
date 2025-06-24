package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookshelfBookMutationDto extends BaseBookshelfBookDto {

    @NotNull(groups = CreateGroup.class)
    @Valid
    protected BookWithIdDto book;

    public BookshelfBookMutationDto(Integer currentPage,
                                    BookReadingStatus status,
                                    RangeDate rangeDate,
                                    List<BookshelfBookNoteDto> notes,
                                    BookWithIdDto book) {
        super(currentPage, status, rangeDate, notes);
        this.book = book;
    }

}
