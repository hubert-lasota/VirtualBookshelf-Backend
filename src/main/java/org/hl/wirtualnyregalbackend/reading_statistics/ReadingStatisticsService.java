package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.MonthlyStatisticsListResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.MonthlyStatisticsResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ReadingStatisticsService {

    private final GenreStatisticsService genreStatsService;
    private final UserReadingStatisticsService userStatsService;
    private final BookLengthStatisticsService bookLenStatsService;


    public MonthlyStatisticsListResponse findMonthlyStatistics(User user) {
        List<GenreStatistics> genreStats = genreStatsService.findGenreStatistics(user);
        List<UserReadingStatistics> userStats = userStatsService.findUserReadingStatistics(user);
        List<BookLengthStatistics> bookLenStats = bookLenStatsService.findBookLengthStatistics(user);

        Locale locale = LocaleContextHolder.getLocale();
        List<MonthlyStatisticsResponse> response = ReadingStatisticsMapper.toMonthlyStatisticsResponse(userStats, genreStats, bookLenStats, locale);
        return new MonthlyStatisticsListResponse(response);
    }

}
