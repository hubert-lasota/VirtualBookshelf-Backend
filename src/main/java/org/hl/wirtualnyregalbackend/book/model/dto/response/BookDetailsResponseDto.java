package org.hl.wirtualnyregalbackend.book.model.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfHeaderResponse;
import org.hl.wirtualnyregalbackend.common.PageResponseDto;
import org.hl.wirtualnyregalbackend.review.model.dto.ReviewResponseDto;

import java.util.Collection;

public record BookDetailsResponseDto(
    @JsonUnwrapped BookResponseDto bookResponseDto,
    Collection<String> tags,
    Float ratingAverage,
    Long ratingTotal,
    PageResponseDto<ReviewResponseDto> ratingPage,
    BookReadingDetailsResponse readingDetails,
    Collection<BookshelfHeaderResponse> bookshelves
) {

}
