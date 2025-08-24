package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;

import java.time.YearMonth;
import java.util.List;

public record MonthlyStatisticsResponse(
    Long readBooks,
    Long readPages,
    Integer mostPagesReadInSession,
    Long readMinutes,
    Integer longestReadMinutes,
    List<GenreStatisticsResponse> genreStatistics,
    List<BookLengthStatistics> bookLengthStatistics,
    YearMonth yearMonth
) {
}
