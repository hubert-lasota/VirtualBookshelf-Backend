package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import java.util.List;

public record StatisticsSummaryResponse(
    long readBookCount,
    long readPageCount,
    long readMinuteCount,
    int mostPagesReadInSession,
    int currentReadingStreak,
    int longestReadingStreak,
    List<GenreStatisticsResponse> genreSummary,
    List<BookLengthStatisticsResponse> bookLengthSummary
) {
}
