package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.YearMonth;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
class GenreStatisticsService {

    private final GenreStatisticsRepository genreStatsRepository;
    private final Clock clock;

    public void updateGenreStatistics(User user, Book book, ReadingStatus status) {
        Set<Genre> genres = book.getGenres();
        genres.forEach(genre -> {
            YearMonth yearMonth = YearMonth.now(clock);
            Optional<GenreStatistics> statsOpt = genreStatsRepository.findByUserIdAndGenreIdAndYearMonth(user.getId(), genre.getId(), yearMonth);
            GenreStatistics stats = statsOpt.orElseGet(() -> new GenreStatistics(genre, user, yearMonth));
            stats.incrementBookCount();
            if (ReadingStatus.READ.equals(status)) {
                stats.incrementReadBookCount();
            }
            genreStatsRepository.save(stats);
        });
    }

}
