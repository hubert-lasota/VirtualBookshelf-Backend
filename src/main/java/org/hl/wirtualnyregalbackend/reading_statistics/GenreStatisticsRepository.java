package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.model.GenreStatsSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
interface GenreStatisticsRepository extends JpaRepository<GenreStatistics, Long>, JpaSpecificationExecutor<GenreStatistics> {

    Optional<GenreStatistics> findByUserAndGenreAndYearMonth(User user, Genre genre, YearMonth yearMonth);

    @Query("""
            SELECT new org.hl.wirtualnyregalbackend.reading_statistics.model.GenreStatsSum(
                SUM(gs.bookCount),
                SUM(gs.readBookCount),
                gs.genre
            )
            FROM GenreStatistics gs
            WHERE gs.user = :user
            GROUP BY gs.genre
        """)
    List<GenreStatsSum> findSummaryStatisticsByUser(User user);

}
