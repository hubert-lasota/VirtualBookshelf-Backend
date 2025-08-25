package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLength;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
interface BookLengthStatisticsRepository extends JpaRepository<BookLengthStatistics, Long> {

    Optional<BookLengthStatistics> findByUserAndLengthAndYearMonth(User user, BookLength length, YearMonth yearMonth);

    List<BookLengthStatistics> findByUser(User user);

}
