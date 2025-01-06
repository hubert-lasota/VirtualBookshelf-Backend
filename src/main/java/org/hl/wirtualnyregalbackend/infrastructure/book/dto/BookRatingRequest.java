package org.hl.wirtualnyregalbackend.infrastructure.book.dto;

import org.springframework.lang.Nullable;

public record BookRatingRequest(byte rating, @Nullable String ratingJustification) {
}
