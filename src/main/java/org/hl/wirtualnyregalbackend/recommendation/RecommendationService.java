package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.recommendation.dao.BookRecommendationRepository;
import org.hl.wirtualnyregalbackend.recommendation.dao.GenreRecommendationRepository;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RecommendationService {

    private final BookRecommendationRepository bookRecommendationRepository;
    private final GenreRecommendationRepository genreRecommendationRepository;
    private final BookRepository bookRepository;

    public RecommendationService(BookRecommendationRepository bookRecommendationRepository,
                                 GenreRecommendationRepository genreRecommendationRepository,
                                 BookRepository bookRepository) {
        this.bookRecommendationRepository = bookRecommendationRepository;
        this.genreRecommendationRepository = genreRecommendationRepository;
        this.bookRepository = bookRepository;
    }

    @Async
    public void saveBookAndGenreRecommendationAsync(Long bookId, User user) {
        Book book = bookRepository.findById(bookId);
        Set<Genre> genres = book.getGenres();

    }

}
