package org.hl.wirtualnyregalbackend.bookshelf_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookReadingStatus;

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


    public BookshelfBookResponseDto(Integer currentPage,
                                    BookReadingStatus status,
                                    Instant startedReadingAt,
                                    Instant finishedReadingAt,
                                    Long id,
                                    Float progressPercentage,
                                    BookResponseDto book,
                                    BookshelfHeaderResponseDto bookshelf,
                                    Long totalNotes) {
        super(currentPage, status, startedReadingAt, finishedReadingAt);
        this.id = id;
        this.progressPercentage = progressPercentage;
        this.book = book;
        this.bookshelf = bookshelf;
        this.totalNotes = totalNotes;
    }

}
