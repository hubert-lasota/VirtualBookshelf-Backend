package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface GenreStatisticsRepository extends JpaRepository<GenreStatistics, Long> {

    @Query("select g from GenreStatistics g where g.user.id = :userId and g.genre.id = :genreId")
    Optional<GenreStatistics> findByUserIdAndGenreId(Long userId, Long genreId);

}
