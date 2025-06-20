package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

@JsonPropertyOrder({"id"})
public class BookResponseDto extends BaseBookDto {

    @JsonProperty
    private final Long id;

    @JsonProperty
    private final BookFormatDto format;

    @JsonProperty
    private final Instant createdAt;

    @JsonProperty
    private final Instant updatedAt;

    @JsonProperty
    private final List<GenreResponseDto> genres;

    public BookResponseDto(String isbn,
                           String title,
                           Integer publicationYear,
                           Integer pageCount,
                           Locale language,
                           String description,
                           String coverUrl,
                           PublisherWithIdDto publisher,
                           List<AuthorWithIdDto> authors,
                           List<GenreResponseDto> genres,
                           List<BookSeriesAssignmentDto> series,
                           Long id,
                           BookFormatDto format,
                           Instant createdAt, Instant updatedAt) {
        super(isbn, title, publicationYear, pageCount, language, description, coverUrl, publisher, authors, series);
        this.id = id;
        this.format = format;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.genres = genres;
    }

}
