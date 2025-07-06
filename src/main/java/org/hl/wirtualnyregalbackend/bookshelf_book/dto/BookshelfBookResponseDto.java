package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;

import java.time.Instant;

@JsonPropertyOrder({"id"})
public class BookshelfBookResponseDto extends BaseBookshelfBookDto {

    @JsonProperty
    @Getter
    private final Long id;

    @JsonProperty
    private final Float progressPercentage;

    @JsonProperty
    private final BookResponseDto book;

    @JsonProperty
    private final BookshelfHeaderResponseDto bookshelf;

    @JsonProperty
    private final Long totalNotes;

    @JsonProperty
    private final Instant createdAt;

    @JsonProperty
    private final Instant updatedAt;


    public BookshelfBookResponseDto(Integer currentPage,
                                    BookReadingStatus status,
                                    RangeDate rangeDate,
                                    Long id,
                                    Float progressPercentage,
                                    BookResponseDto book,
                                    BookshelfHeaderResponseDto bookshelf,
                                    Long totalNotes,
                                    Instant createdAt,
                                    Instant updatedAt) {
        super(currentPage, status, rangeDate);
        this.id = id;
        this.progressPercentage = progressPercentage;
        this.book = book;
        this.bookshelf = bookshelf;
        this.totalNotes = totalNotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
