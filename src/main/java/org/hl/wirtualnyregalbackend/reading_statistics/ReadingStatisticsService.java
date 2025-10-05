package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.*;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.model.GenreStatsSum;
import org.hl.wirtualnyregalbackend.reading_statistics.model.UserStatsSum;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ReadingStatisticsService {

    private final GenreStatisticsService genreStatsService;
    private final UserReadingStatisticsService userStatsService;
    private final BookLengthStatisticsService bookLenStatsService;


    public MonthlyStatisticsListResponse findMonthlyStatistics(User user, @Nullable Integer year) {
        List<GenreStatistics> genreStats = genreStatsService.findGenreStatistics(user, year);
        List<UserReadingStatistics> userStats = userStatsService.findReadingStatistics(user, year);
        List<BookLengthStatistics> bookLenStats = bookLenStatsService.findBookLengthStatistics(user, year);

        Locale locale = LocaleContextHolder.getLocale();
        List<MonthlyStatisticsResponse> response = ReadingStatisticsMapper.toMonthlyStatisticsResponse(userStats, genreStats, bookLenStats, locale);
        return new MonthlyStatisticsListResponse(response);
    }

    public YearListResponse findAvailableYearsByUser(User user) {
        return userStatsService.findAvailableYearsByUser(user);
    }

    public StatisticsSummaryResponse findStatisticsSummary(User user) {
        UserStatsSum userSum = userStatsService.findStatisticsSummaryByUser(user);
        List<GenreStatsSum> genreSumList = genreStatsService.findSummaryStatisticsByUser(user);
        List<BookLengthStatisticsResponse> bookLenSumList = bookLenStatsService.findStatisticsSummaryByUser(user);
        return ReadingStatisticsMapper.toStatisticsSummaryResponse(userSum, genreSumList, bookLenSumList, LocaleContextHolder.getLocale());
    }

}
