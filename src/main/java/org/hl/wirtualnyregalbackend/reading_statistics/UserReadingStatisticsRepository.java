package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.model.UserStatsSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
interface UserReadingStatisticsRepository extends JpaRepository<UserReadingStatistics, Long>, JpaSpecificationExecutor<UserReadingStatistics> {

    Optional<UserReadingStatistics> findByUserAndYearMonth(User user, YearMonth yearMonth);

    @Query("""
            SELECT DISTINCT CAST(SUBSTRING(urs.yearMonth, 1, 4) AS integer)
            FROM UserReadingStatistics urs
            WHERE urs.user = :user
            ORDER BY CAST(SUBSTRING(urs.yearMonth, 1, 4) AS integer) DESC
        """)
    List<Integer> findDistinctYearsByUser(User user);

    @Query("""
            SELECT new org.hl.wirtualnyregalbackend.reading_statistics.model.UserStatsSum(
                SUM(urs.readBookCount),
                SUM(urs.readPageCount),
                SUM(urs.readMinuteCount),
                MAX(urs.longestReadMinutes),
                MAX(urs.longestReadingStreak),
                (SELECT urs2.currentReadingStreak
                 FROM UserReadingStatistics urs2
                 WHERE urs2.user = :user
                 ORDER BY urs2.yearMonth DESC
                 LIMIT 1),
                MAX(urs.mostPagesReadInSession)
            )
            FROM UserReadingStatistics urs
            WHERE urs.user = :user
        """)
    UserStatsSum findStatisticsSummaryByUser(User user);

}
