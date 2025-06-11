package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;

import java.time.Instant;
import java.util.List;

@JsonPropertyOrder({"id"})
public class BookshelfBookResponseDto extends BaseBookshelfBookDto {

    @JsonProperty
    private final Long id;

    @JsonProperty
    private final Integer progressPercentage;

    @JsonProperty
    private final BookResponseDto book;

    @JsonProperty
    private final Instant createdAt;

    @JsonProperty
    private final Instant updatedAt;


    public BookshelfBookResponseDto(Integer currentPage,
                                    BookReadingStatus status,
                                    RangeDate rangeDate,
                                    List<BookshelfBookNoteDto> notes,
                                    Long id,
                                    Integer progressPercentage,
                                    BookResponseDto book,
                                    Instant createdAt,
                                    Instant updatedAt) {
        super(currentPage, status, rangeDate, notes);
        this.id = id;
        this.progressPercentage = progressPercentage;
        this.book = book;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
