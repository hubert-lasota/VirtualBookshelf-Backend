package org.hl.wirtualnyregalbackend.reading_statistics.model;

public record UserStatsSum(
    long readBookCount,
    long readPageCount,
    long readMinuteCount,
    int longestReadMinutes,
    int longestReadingStreak,
    int currentReadingStreak,
    int mostPagesReadInSession
) {
}
