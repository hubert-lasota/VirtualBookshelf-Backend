package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;

public record GenreStatisticsResponse(
    Long bookCount,
    Long readBookCount,
    GenreResponse genre
) {
}
