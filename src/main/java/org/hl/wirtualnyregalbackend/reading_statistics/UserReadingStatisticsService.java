package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.YearListResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.model.UserStatsSum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class UserReadingStatisticsService {

    private final UserReadingStatisticsRepository repository;
    private final Clock clock;

    public void updateTotalReadPagesAndMinutes(Integer readPages, Integer readMinutes, User user) {
        UserReadingStatistics stats = findOrCreateUserReadingStatistics(user);
        stats.addReadPages(readPages);
        stats.addReadMinutes(readMinutes);
        repository.save(stats);
    }

    public void incrementReadBookCount(User user) {
        UserReadingStatistics stats = findOrCreateUserReadingStatistics(user);
        stats.incrementReadBookCount();
        repository.save(stats);
    }

    public void decrementReadBookCount(User user) {
        UserReadingStatistics stats = findOrCreateUserReadingStatistics(user);
        stats.decrementReadBookCount();
        repository.save(stats);
    }

    public void updateReadingStreak(Instant lastReadAt, User user) {
        UserReadingStatistics stats = findOrCreateUserReadingStatistics(user);
        LocalDate lastReadDate = LocalDate.ofInstant(lastReadAt, clock.getZone());
        LocalDate yesterday = LocalDate.now(clock).minusDays(1);
        if (!yesterday.equals(lastReadDate)) {
            stats.resetCurrentReadingStreak();
        }
        stats.incrementCurrentReadingStreak();
        repository.save(stats);
    }

    public List<UserReadingStatistics> findReadingStatistics(User user, @Nullable Integer year) {
        Specification<UserReadingStatistics> spec = StatisticsSpecification.byUserId(user.getId());
        if (year != null) {
            spec = spec.and(StatisticsSpecification.byYear(year));
        }
        return repository.findAll(spec);
    }

    public YearListResponse findAvailableYearsByUser(User user) {
        List<Integer> years = repository.findDistinctYearsByUser(user);
        return new YearListResponse(years);
    }

    public UserStatsSum findStatisticsSummaryByUser(User user) {
        return repository.findStatisticsSummaryByUser(user);
    }

    private UserReadingStatistics findOrCreateUserReadingStatistics(User user) {
        YearMonth yearMonth = YearMonth.now(clock);
        Optional<UserReadingStatistics> statsOpt = repository.findByUserAndYearMonth(user, yearMonth);
        return statsOpt.orElseGet(() -> new UserReadingStatistics(user, yearMonth));
    }

}
