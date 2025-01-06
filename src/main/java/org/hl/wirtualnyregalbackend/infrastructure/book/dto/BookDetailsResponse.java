package org.hl.wirtualnyregalbackend.infrastructure.book.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Collection;

public record BookDetailsResponse(@JsonUnwrapped BookResponse bookResponse,
                                  Collection<String> genres,
                                  Collection<BookRatingResponse> ratings) {
}
