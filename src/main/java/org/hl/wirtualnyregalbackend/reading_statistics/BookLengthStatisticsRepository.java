package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.BookLengthStatisticsResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.model.BookLength;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
interface BookLengthStatisticsRepository extends JpaRepository<BookLengthStatistics, Long>, JpaSpecificationExecutor<BookLengthStatistics> {

    Optional<BookLengthStatistics> findByUserAndLengthAndYearMonth(User user, BookLength length, YearMonth yearMonth);

    @Query("""
            SELECT new org.hl.wirtualnyregalbackend.reading_statistics.dto.BookLengthStatisticsResponse(
                bls.length,
                SUM(bls.bookCount),
                SUM(bls.readBookCount)
            )
            FROM BookLengthStatistics bls
            WHERE bls.user = :user
            GROUP BY bls.length
        """)
    List<BookLengthStatisticsResponse> findStatisticsSummaryByUser(User user);

}
