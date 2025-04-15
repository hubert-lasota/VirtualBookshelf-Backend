package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.book.dao.BookReadingDetailsRepository;
import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.book.model.dto.request.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.genre.dao.GenreRepository;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.review.book_review.dao.BookReviewRepository;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class BookService {

    private final ApplicationEventPublisher eventPublisher;
    private final BookRepository bookRepository;
    private final BookReviewRepository bookReviewRepository;
    private final BookReadingDetailsRepository bookReadingDetailsRepository;
    private final GenreRepository genreRepository;
    private final BookshelfService bookshelfService;
    private final AuthorService authorService;
    private final PublisherService publisherService;


    public BookService(ApplicationEventPublisher eventPublisher,
                       BookRepository bookRepository,
                       BookReadingDetailsRepository bookReadingDetailsRepository,
                       GenreRepository genreRepository,
                       BookReviewRepository bookReviewRepository,
                       BookshelfService bookshelfService,
                       AuthorService authorService,
                       PublisherService publisherService) {
        this.eventPublisher = eventPublisher;
        this.bookRepository = bookRepository;
        this.bookReadingDetailsRepository = bookReadingDetailsRepository;
        this.genreRepository = genreRepository;
        this.bookReviewRepository = bookReviewRepository;
        this.bookshelfService = bookshelfService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    public BookResponseDto updateBook(Long bookId, BookMutationDto bookMutationDto) {
        return null;
    }

    public Page<BookSearchResponseDto> searchBooks(String query, Pageable pageable) {
        return null;
    }

    public BookResponseDto findBookById(Long bookId, User user) {
        Book book = bookRepository.findById(bookId);
        BookFoundEvent event = new BookFoundEvent(book, user);
        eventPublisher.publishEvent(event);
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }
}
