package org.hl.wirtualnyregalbackend.bookshelf.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.book.model.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.BaseResponseDto;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;

import java.time.Instant;
import java.util.List;

public class BookshelfBookResponseDto extends BaseResponseDto {

    @JsonProperty
    private final BookResponseDto book;

    @JsonProperty
    private final Integer currentPage;

    @JsonProperty
    private final Integer progressPercentage;

    @JsonProperty
    private final BookReadingStatus status;

    @JsonProperty
    @JsonUnwrapped
    private final RangeDate rangeDate;

    @JsonProperty
    private final List<BookshelfBookNoteDto> notes;


    public BookshelfBookResponseDto(Long id,
                                    Instant createdAt,
                                    Instant updatedAt,
                                    BookResponseDto book,
                                    Integer currentPage,
                                    Integer progressPercentage,
                                    BookReadingStatus status,
                                    RangeDate rangeDate,
                                    List<BookshelfBookNoteDto> notes) {
        super(id, createdAt, updatedAt);
        this.book = book;
        this.currentPage = currentPage;
        this.progressPercentage = progressPercentage;
        this.status = status;
        this.rangeDate = rangeDate;
        this.notes = notes;
    }

}
