package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;

import java.util.List;
import java.util.Locale;


public record BookResponseDto(
    Long id,
    String isbn,
    String title,
    List<AuthorWithIdDto> authors,
    String coverUrl,
    BookFormatDto format,
    List<GenreResponseDto> genres,
    ReviewStats reviewStatistics,
    PublisherWithIdDto publisher,
    Integer pageCount,
    Integer publicationYear,
    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageCode")
    Locale language,
    String description
) {
}