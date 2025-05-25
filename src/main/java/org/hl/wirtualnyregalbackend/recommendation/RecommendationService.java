package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class RecommendationService {

    private final BookRecommendationRepository bookRecommendationRepository;
    private final GenreRecommendationRepository genreRecommendationRepository;
    private final BookService bookService;

    public RecommendationService(BookRecommendationRepository bookRecommendationRepository,
                                 GenreRecommendationRepository genreRecommendationRepository,
                                 BookService bookService) {
        this.bookRecommendationRepository = bookRecommendationRepository;
        this.genreRecommendationRepository = genreRecommendationRepository;
        this.bookService = bookService;
    }

    @Async
    public void saveBookAndGenreRecommendationAsync(Long bookId, User user) {
        Book book = bookService.findBookById(bookId);
        Set<Genre> genres = book.getGenres();

    }

}
