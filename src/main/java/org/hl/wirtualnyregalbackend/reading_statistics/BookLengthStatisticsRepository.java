package org.hl.wirtualnyregalbackend.reading_statistics;

import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLength;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BookLengthStatisticsRepository extends JpaRepository<BookLengthStatistics, Long> {

    @Query("select b from BookLengthStatistics b where b.user.id = :userId and b.length = :length")
    Optional<BookLengthStatistics> findByUserIdAndLength(Long userId, BookLength length);

}
