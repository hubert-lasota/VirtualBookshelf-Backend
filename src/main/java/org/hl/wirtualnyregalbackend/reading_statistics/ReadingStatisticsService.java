package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.MonthlyStatisticsListResponse;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReadingStatisticsService {

    private final GenreStatisticsService genreStatsService;
    private final UserReadingStatisticsService userStatsService;
    private final BookLengthStatisticsService bookLenStatsService;


    public MonthlyStatisticsListResponse findMonthlyStatistics(User user) {


        return null;
    }

}
