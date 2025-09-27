package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponse;
import org.hl.wirtualnyregalbackend.reading_book.dto.BookshelfSummaryResponse;

import java.util.List;
import java.util.Locale;

public record BookDetailsResponse(
    Long id,
    String isbn,
    String title,
    List<AuthorResponse> authors,
    String coverUrl,
    BookFormatDto format,
    List<GenreResponse> genres,
    Integer totalReviews,
    Double averageRating,
    ReviewResponse review,
    PublisherResponse publisher,
    Integer pageCount,
    Integer publicationYear,
    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageCode")
    Locale language,
    String description,
    BookshelfSummaryResponse bookshelf
) {
}
