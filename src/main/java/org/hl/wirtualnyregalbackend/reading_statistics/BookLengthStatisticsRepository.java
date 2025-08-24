package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLength;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Optional;

@Repository
interface BookLengthStatisticsRepository extends JpaRepository<BookLengthStatistics, Long> {

    @Query("select b from BookLengthStatistics b where b.user.id = :userId and b.length = :length and b.yearMonth = :yearMonth")
    Optional<BookLengthStatistics> findByUserIdAndLengthAndYearMonth(Long userId, BookLength length, YearMonth yearMonth);

}
