package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
interface GenreStatisticsRepository extends JpaRepository<GenreStatistics, Long> {

    Optional<GenreStatistics> findByUserAndGenreAndYearMonth(User user, Genre genre, YearMonth yearMonth);

    List<GenreStatistics> findByUser(User user);

}
