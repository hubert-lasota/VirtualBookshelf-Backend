package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import java.util.List;

public record StatisticsResponse(
    List<MonthlyStatisticsResponse> monthlyStatistics,
    Long totalReadBooks,
    Long totalReadPages,
    Integer mostPagesReadInSession,
    Long totalReadMinutes,
    Integer longestReadMinutes,
    Integer currentReadingStreak,
    Integer longestReadingStreak
) {
}
