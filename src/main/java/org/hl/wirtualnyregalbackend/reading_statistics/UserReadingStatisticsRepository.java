package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.reading_statistics.entity.UserReadingStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserReadingStatisticsRepository extends JpaRepository<UserReadingStatistics, Long> {

    Optional<UserReadingStatistics> findByUserId(Long userId);

}
