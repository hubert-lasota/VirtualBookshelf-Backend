package org.hl.wirtualnyregalbackend.recommendation;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.BookQueryService;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.recommendation.entity.AuthorRecommendation;
import org.hl.wirtualnyregalbackend.recommendation.entity.BookRecommendation;
import org.hl.wirtualnyregalbackend.recommendation.entity.GenreRecommendation;
import org.hl.wirtualnyregalbackend.user.UserQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class RecommendationService {

    private final BookRecommendationRepository bookRecommendationRepository;
    private final AuthorRecommendationRepository authorRecommendationRepository;
    private final GenreRecommendationRepository genreRecommendationRepository;
    private final BookQueryService bookQuery;
    private final UserQueryService userQuery;


    @Transactional
    public void boostScoresForBook(Long bookId, Long userId) {
        Book book = bookQuery.findBookById(bookId);
        User user = userQuery.findUserById(userId);
        BookRecommendation br = findOrCreateBookRecommendation(book, user);
        br.boostScore();
        boostAuthorsAndGenresScore(book, user);
    }

    @Transactional
    public void reduceScoresForBook(Long bookId, Long userId) {
        Book book = bookQuery.findBookById(bookId);
        User user = userQuery.findUserById(userId);
        BookRecommendation br = findOrCreateBookRecommendation(book, user);
        br.reduceScore();
        reduceAuthorsAndGenresScore(book, user);
    }

    @Transactional
    public void boostAuthorsAndGenresScore(Long bookId, Long userId) {
        Book book = bookQuery.findBookById(bookId);
        User user = userQuery.findUserById(userId);
        boostAuthorsAndGenresScore(book, user);
    }

    @Transactional
    public void boostAuthorsAndGenresScore(Book book, User user) {
        book
            .getAuthors()
            .forEach((author) -> findOrCreateAuthorRecommendation(author, user).boostScore());
        book
            .getGenres()
            .forEach((genre) -> findOrCreateGenreRecommendation(genre, user).boostScore());
    }

    @Transactional
    public void reduceAuthorsAndGenresScore(Long bookId, Long userId) {
        Book book = bookQuery.findBookById(bookId);
        User user = userQuery.findUserById(userId);
        reduceAuthorsAndGenresScore(book, user);
    }

    @Transactional
    public void reduceAuthorsAndGenresScore(Book book, User user) {
        book
            .getAuthors()
            .forEach((author) -> findOrCreateAuthorRecommendation(author, user).reduceScore());
        book
            .getGenres()
            .forEach((genre) -> findOrCreateGenreRecommendation(genre, user).reduceScore());
    }

    private BookRecommendation findOrCreateBookRecommendation(Book book, User user) {
        return bookRecommendationRepository
            .findByBookAndUser(book, user)
            .orElseGet(() -> bookRecommendationRepository.save(new BookRecommendation(user, book)));
    }

    private AuthorRecommendation findOrCreateAuthorRecommendation(Author author, User user) {
        return authorRecommendationRepository
            .findByAuthorAndUser(author, user)
            .orElseGet(() -> authorRecommendationRepository.save(new AuthorRecommendation(user, author)));
    }

    private GenreRecommendation findOrCreateGenreRecommendation(Genre genre, User user) {
        return genreRecommendationRepository
            .findByGenreAndUser(genre, user)
            .orElseGet(() -> genreRecommendationRepository.save(new GenreRecommendation(user, genre)));
    }

}
