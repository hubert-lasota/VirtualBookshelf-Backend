package org.hl.wirtualnyregalbackend.reading_book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingStatus;

import java.time.Instant;

@JsonPropertyOrder({"id"})
public class ReadingBookResponseDto extends BaseReadingBookDto {

    @JsonProperty
    @Getter
    private final Long id;

    @JsonProperty
    private final Float progressPercentage;

    @JsonProperty
    private final Integer currentPage;

    @JsonProperty
    private final BookResponseDto book;

    @JsonProperty
    private final BookshelfHeaderResponseDto bookshelf;

    @JsonProperty
    private final Long totalNotes;


    public ReadingBookResponseDto(ReadingStatus status,
                                  Instant startedReadingAt,
                                  Instant finishedReadingAt,
                                  Long id,
                                  Float progressPercentage,
                                  Integer currentPage,
                                  BookResponseDto book,
                                  BookshelfHeaderResponseDto bookshelf,
                                  Long totalNotes) {
        super(status, startedReadingAt, finishedReadingAt);
        this.id = id;
        this.progressPercentage = progressPercentage;
        this.currentPage = currentPage;
        this.book = book;
        this.bookshelf = bookshelf;
        this.totalNotes = totalNotes;
    }

}
