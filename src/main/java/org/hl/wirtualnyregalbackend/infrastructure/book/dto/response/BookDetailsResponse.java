package org.hl.wirtualnyregalbackend.infrastructure.book.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto.BookshelfHeaderResponse;
import org.springframework.data.domain.Page;

import java.util.Collection;

public record BookDetailsResponse(@JsonUnwrapped BookResponse bookResponse,
                                  Collection<String> tags,
                                  Float ratingAverage,
                                  Long ratingTotal,
                                  Page<BookRatingResponse> ratingPage,
                                  BookReadingDetailsResponse readingDetails,
                                  Collection<BookshelfHeaderResponse> bookshelves) {
}
