package org.hl.wirtualnyregalbackend.book_series.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Instant;

public record BookSeriesResponseDto(
    Long id,
    @JsonUnwrapped
    BookSeriesMutationDto dto,
    Instant createdAt,
    Instant updatedAt
) {
}
