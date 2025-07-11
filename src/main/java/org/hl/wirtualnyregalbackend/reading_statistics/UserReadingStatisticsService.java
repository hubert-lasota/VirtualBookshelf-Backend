package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
class UserReadingStatisticsService {

    private final UserReadingStatisticsRepository userStatsRepository;
    private final Clock clock;

    public void updateTotalReadPagesAndMinutes(Integer readPages, Integer readMinutes, User user) {
        UserReadingStatistics stats = findOrCreateUserReadingStatistics(user);
        stats.addReadPages(readPages);
        stats.addReadMinutes(readMinutes);
        userStatsRepository.save(stats);
    }

    public void updateTotalReadBooks(User user) {
        UserReadingStatistics stats = findOrCreateUserReadingStatistics(user);
        stats.incrementTotalReadBooks();
        userStatsRepository.save(stats);
    }

    public void updateReadingStreak(Instant lastReadAt, User user) {
        UserReadingStatistics stats = findOrCreateUserReadingStatistics(user);
        LocalDate lastReadDate = LocalDate.ofInstant(lastReadAt, clock.getZone());
        LocalDate yesterday = LocalDate.now(clock).minusDays(1);
        if (!yesterday.equals(lastReadDate)) {
            stats.resetCurrentReadingStreak();
        }
        stats.incrementCurrentReadingStreak();
        userStatsRepository.save(stats);
    }

    private UserReadingStatistics findOrCreateUserReadingStatistics(User user) {
        Optional<UserReadingStatistics> statsOpt = userStatsRepository.findByUserId(user.getId());
        return statsOpt.orElseGet(() -> new UserReadingStatistics(user));
    }

}
