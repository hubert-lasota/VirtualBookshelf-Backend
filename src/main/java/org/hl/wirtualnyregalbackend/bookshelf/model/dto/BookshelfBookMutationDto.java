package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

import java.util.List;

public record BookshelfBookMutationDto(
    @NotNull(groups = CreateGroup.class)
    Long bookId,

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer currentPage,

    @NotNull(groups = CreateGroup.class)
    BookReadingStatus status,

    @NotNull(groups = CreateGroup.class)
    @Valid
    RangeDate rangeDate,

    List<BookshelfBookNoteDto> notes
) {
}
