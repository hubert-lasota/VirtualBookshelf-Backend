package org.hl.wirtualnyregalbackend.book.model.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public record BookRatingRequest(@NotNull Float rating, @Nullable String ratingJustification) {
}
