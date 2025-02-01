package org.hl.wirtualnyregalbackend.application.recommendation;

import org.hl.wirtualnyregalbackend.application.book.Book;
import org.hl.wirtualnyregalbackend.application.book.BookGenre;
import org.hl.wirtualnyregalbackend.application.book.BookService;
import org.hl.wirtualnyregalbackend.infrastructure.recommendation.BookGenreRecommendationRepository;
import org.hl.wirtualnyregalbackend.infrastructure.recommendation.BookRecommendationRepository;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RecommendationService {

    private final BookRecommendationRepository bookRecommendationRepository;
    private final BookGenreRecommendationRepository bookGenreRecommendationRepository;
    private final BookService bookService;

    public RecommendationService(BookRecommendationRepository bookRecommendationRepository,
                                 BookGenreRecommendationRepository bookGenreRecommendationRepository,
                                 BookService bookService) {
        this.bookRecommendationRepository = bookRecommendationRepository;
        this.bookGenreRecommendationRepository = bookGenreRecommendationRepository;
        this.bookService = bookService;
    }

    @Async
    public void saveBookAndGenreRecommendationAsync(String bookId, User user) {
        Book book = bookService.findBookById(bookId, true);
        Set<BookGenre> bookGenres = book.getGenres();

    }

}
