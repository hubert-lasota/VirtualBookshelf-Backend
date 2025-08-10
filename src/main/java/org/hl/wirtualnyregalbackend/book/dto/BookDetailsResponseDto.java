package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;

import java.util.List;
import java.util.Locale;

public record BookDetailsResponseDto(
    Long id,
    String isbn,
    String title,
    List<AuthorResponseDto> authors,
    String coverUrl,
    BookFormatDto format,
    List<GenreResponseDto> genres,
    ReviewStatistics reviewStatistics,
    PublisherResponseDto publisher,
    Integer pageCount,
    Integer publicationYear,
    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageCode")
    Locale language,
    String description
) {
}
