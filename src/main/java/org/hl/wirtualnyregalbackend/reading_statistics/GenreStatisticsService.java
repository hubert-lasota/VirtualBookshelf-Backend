package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.model.GenreStatsSum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
class GenreStatisticsService {

    private final GenreStatisticsRepository repository;
    private final Clock clock;

    public void updateGenreStatistics(User user, Book book, ReadingStatus status) {
        Set<Genre> genres = book.getGenres();
        genres.forEach(genre -> {
            YearMonth yearMonth = YearMonth.now(clock);
            Optional<GenreStatistics> statsOpt = repository.findByUserAndGenreAndYearMonth(user, genre, yearMonth);
            GenreStatistics stats = statsOpt.orElseGet(() -> new GenreStatistics(genre, user, yearMonth));
            stats.incrementBookCount();
            if (ReadingStatus.READ.equals(status)) {
                stats.incrementReadBookCount();
            }
            repository.save(stats);
        });
    }

    public List<GenreStatistics> findGenreStatistics(User user, @Nullable Integer year) {
        Specification<GenreStatistics> spec = StatisticsSpecification.byUserId(user.getId());
        if (year != null) {
            spec = spec.and(StatisticsSpecification.byYear(year));
        }
        return repository.findAll(spec);
    }

    public List<GenreStatsSum> findSummaryStatisticsByUser(User user) {
        return repository.findSummaryStatisticsByUser(user);
    }

}
