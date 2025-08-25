package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
interface UserReadingStatisticsRepository extends JpaRepository<UserReadingStatistics, Long> {

    Optional<UserReadingStatistics> findByUserAndYearMonth(User user, YearMonth yearMonth);

    List<UserReadingStatistics> findByUser(User user);

}
