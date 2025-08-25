package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.BookLengthStatisticsResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.GenreStatisticsResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.MonthlyStatisticsResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;

import java.time.YearMonth;
import java.util.List;
import java.util.Locale;

class ReadingStatisticsMapper {

    private ReadingStatisticsMapper() {
    }


    public static List<MonthlyStatisticsResponse> toMonthlyStatisticsResponse(List<UserReadingStatistics> userStats,
                                                                              List<GenreStatistics> genreStats,
                                                                              List<BookLengthStatistics> bookLenStats,
                                                                              Locale locale) {
        return userStats
            .stream()
            .map(userStat -> {
                YearMonth yearMonth = userStat.getYearMonth();
                List<GenreStatisticsResponse> gsr = filterGenreStatsByYearMonth(genreStats, yearMonth, locale);
                List<BookLengthStatisticsResponse> blsr = filterBookLenStatsByYearMonth(bookLenStats, yearMonth);
                return new MonthlyStatisticsResponse(
                    userStat.getReadBookCount(),
                    userStat.getReadPageCount(),
                    userStat.getMostPagesReadInSession(),
                    userStat.getReadMinuteCount(),
                    userStat.getLongestReadMinutes(),
                    gsr,
                    blsr,
                    yearMonth
                );
            })
            .toList();
    }

    private static List<GenreStatisticsResponse> filterGenreStatsByYearMonth(List<GenreStatistics> genreStats,
                                                                             YearMonth yearMonth,
                                                                             Locale locale) {
        return genreStats.stream()
            .filter(stats -> stats.getYearMonth().equals(yearMonth))
            .map(stats -> toGenreStatisticsResponse(stats, locale))
            .toList();
    }

    private static GenreStatisticsResponse toGenreStatisticsResponse(GenreStatistics genreStats, Locale locale) {
        GenreResponse genre = GenreMapper.toGenreResponse(genreStats.getGenre(), locale);
        return new GenreStatisticsResponse(
            genreStats.getBookCount(),
            genreStats.getReadBookCount(),
            genre
        );
    }

    private static List<BookLengthStatisticsResponse> filterBookLenStatsByYearMonth(List<BookLengthStatistics> bookLenStats,
                                                                                    YearMonth yearMonth) {
        return bookLenStats.stream()
            .filter(stats -> stats.getYearMonth().equals(yearMonth))
            .map(ReadingStatisticsMapper::toBookLengthStatisticsResponse)
            .toList();
    }


    private static BookLengthStatisticsResponse toBookLengthStatisticsResponse(BookLengthStatistics lenStats) {
        return new BookLengthStatisticsResponse(
            lenStats.getLength(),
            lenStats.getBookCount(),
            lenStats.getReadBookCount()
        );
    }

}
