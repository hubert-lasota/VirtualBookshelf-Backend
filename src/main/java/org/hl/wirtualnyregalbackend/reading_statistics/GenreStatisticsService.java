package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.GenreStatistics;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
class GenreStatisticsService {

    private final GenreStatisticsRepository genreStatsRepository;

    @Transactional
    public void updateGenreStatistics(User user, Book book) {
        Set<Genre> genres = book.getGenres();
        genres.forEach(genre -> {
            Optional<GenreStatistics> statsOpt = genreStatsRepository.findByUserIdAndGenreId(user.getId(), genre.getId());
            if (statsOpt.isPresent()) {
                GenreStatistics stats = statsOpt.get();
                stats.incrementGenreCount();
            } else {
                GenreStatistics stats = new GenreStatistics(genre, 1L, user);
                genreStatsRepository.save(stats);
            }
        });
    }

}
