package org.hl.wirtualnyregalbackend.reading_book.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingStatus;

import java.time.Instant;

@JsonPropertyOrder({"id"})
public record ReadingBookResponseDto(
    Long id,
    Float progressPercentage,
    Integer currentPage,
    Long totalNotes,
    Instant startedReadingAt,
    Instant finishedReadingAt,
    ReadingStatus status,
    BookResponseDto book,
    BookshelfHeaderResponseDto bookshelf
) {

}
