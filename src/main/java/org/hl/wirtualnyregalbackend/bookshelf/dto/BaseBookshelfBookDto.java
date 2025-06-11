package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

import java.util.List;

abstract class BaseBookshelfBookDto {

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    @Min(1)
    protected final Integer currentPage;

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    protected final BookReadingStatus status;

    @JsonProperty
    @JsonUnwrapped
    @NotNull(groups = CreateGroup.class)
    @Valid
    protected final RangeDate rangeDate;

    @JsonProperty
    @Valid
    protected final List<BookshelfBookNoteDto> notes;


    protected BaseBookshelfBookDto(Integer currentPage,
                                   BookReadingStatus status,
                                   RangeDate rangeDate,
                                   List<BookshelfBookNoteDto> notes) {
        this.currentPage = currentPage;
        this.status = status;
        this.rangeDate = rangeDate;
        this.notes = notes;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public BookReadingStatus getStatus() {
        return status;
    }

    public RangeDate getRangeDate() {
        return rangeDate;
    }

    public List<BookshelfBookNoteDto> getNotes() {
        return notes;
    }

}
