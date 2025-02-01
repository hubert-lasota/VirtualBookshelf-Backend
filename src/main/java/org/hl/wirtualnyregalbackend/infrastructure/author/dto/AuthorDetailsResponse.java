package org.hl.wirtualnyregalbackend.infrastructure.author.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.response.BookResponse;
import org.springframework.data.domain.Page;

import java.util.Collection;

public record AuthorDetailsResponse(@JsonUnwrapped AuthorResponse authorResponse,
                                    Page<BookResponse> bookPage,
                                    Page<AuthorRatingResponse> ratingPage,
                                    Float averageRating,
                                    Collection<String> tags) {
}
