package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import java.time.YearMonth;
import java.util.List;

public record MonthlyStatisticsResponse(
    Long readBookCount,
    Long readPageCount,
    Integer mostPagesReadInSession,
    Long readMinuteCount,
    Integer longestReadMinutes,
    List<GenreStatisticsResponse> genreStatistics,
    List<BookLengthStatisticsResponse> bookLengthStatistics,
    YearMonth yearMonth
) {
}
