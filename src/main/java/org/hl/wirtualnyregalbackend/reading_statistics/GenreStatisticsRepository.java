package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Optional;

@Repository
interface GenreStatisticsRepository extends JpaRepository<GenreStatistics, Long> {

    @Query("select g from GenreStatistics g where g.user.id = :userId and g.genre.id = :genreId and g.yearMonth = :yearMonth")
    Optional<GenreStatistics> findByUserIdAndGenreIdAndYearMonth(Long userId, Long genreId, YearMonth yearMonth);

}
