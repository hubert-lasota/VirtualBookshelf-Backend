package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
class GenreStatisticsService {

    private final GenreStatisticsRepository genreStatsRepository;


    public void updateGenreStatistics(User user, Book book, ReadingStatus status) {
        Set<Genre> genres = book.getGenres();
        genres.forEach(genre -> {
            Optional<GenreStatistics> statsOpt = genreStatsRepository.findByUserIdAndGenreId(user.getId(), genre.getId());
            GenreStatistics stats = statsOpt.orElseGet(() -> new GenreStatistics(user));
            stats.incrementBookCount();
            if (ReadingStatus.READ.equals(status)) {
                stats.incrementReadBookCount();
            }
            genreStatsRepository.save(stats);
        });
    }

}
