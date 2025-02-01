package org.hl.wirtualnyregalbackend.infrastructure.book.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public record BookRatingRequest(@NotNull Float rating, @Nullable String ratingJustification) {
}
