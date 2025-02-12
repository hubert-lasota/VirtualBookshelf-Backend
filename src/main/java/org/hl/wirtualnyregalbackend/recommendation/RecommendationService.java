package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.model.Book;
import org.hl.wirtualnyregalbackend.book.model.BookGenre;
import org.hl.wirtualnyregalbackend.security.model.User;
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
