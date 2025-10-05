package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import java.time.YearMonth;
import java.util.List;

public record MonthlyStatisticsResponse(
    long readBookCount,
    long readPageCount,
    int mostPagesReadInSession,
    long readMinuteCount,
    int longestReadMinutes,
    List<GenreStatisticsResponse> genreStatistics,
    List<BookLengthStatisticsResponse> bookLengthStatistics,
    YearMonth yearMonth
) {
}
