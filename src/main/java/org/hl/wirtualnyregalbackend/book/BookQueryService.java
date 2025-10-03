package org.hl.wirtualnyregalbackend.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.dto.BookDetailsResponse;
import org.hl.wirtualnyregalbackend.book.dto.BookPageResponse;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
import org.hl.wirtualnyregalbackend.book_review.BookReviewQueryService;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookQueryService;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookQueryService {

    private final BookRepository repository;
    private final BookSpecification bookSpec;
    private final ApplicationEventPublisher eventPublisher;
    private final BookReviewQueryService reviewQuery;
    private final ReadingBookQueryService readingBookQuery;


    public BookPageResponse findRecommendedBooks(User recommendedFor, BookFilter filter, Pageable pageable) {
        return findBooks(recommendedFor, filter, pageable);
    }

    BookPageResponse findBooks(BookFilter filter, Pageable pageable) {
        return findBooks(null, filter, pageable);
    }

    private BookPageResponse findBooks(User recommendedFor, BookFilter filter, Pageable pageable) {
        var spec = bookSpec.byFilter(filter);
        if (recommendedFor != null) {
            spec = spec.and(bookSpec.sortByRecommendation(recommendedFor));
        }
        Locale locale = LocaleContextHolder.getLocale();
        var bookPage = repository
            .findAll(spec, pageable)
            .map((book) -> BookMapper.toBookResponse(book, locale));
        return BookPageResponse.from(bookPage);
    }


    BookDetailsResponse findBookDetailsById(Long bookId, User user) {
        Book book = findBookById(bookId);
        BookFoundEvent event = BookFoundEvent.of(book, user);
        eventPublisher.publishEvent(event);

        BookReview review = reviewQuery.findBookReviewByBookIdAndUserId(bookId, user.getId());
        Locale locale = LocaleContextHolder.getLocale();
        ReadingBook rb = readingBookQuery.findUserReadingBookByBookId(bookId, user);

        BookDetailsResponse response = BookMapper.toBookDetailsResponse(book, review, locale, rb);
        log.info("Found Book Details: {}", response);
        return response;
    }

    public Book findBookById(Long id) throws BookNotFoundException {
        Optional<Book> bookOpt = findBookOptById(id);
        return bookOpt.orElseThrow(() -> {
            log.warn("Book not found with ID: {}", id);
            return new BookNotFoundException(id);
        });
    }

    Optional<Book> findBookOptById(@Nullable Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

}
