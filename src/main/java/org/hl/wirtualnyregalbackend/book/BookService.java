package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.book.model.dto.BookDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookEditionDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.book_reading.dao.BookReadingDetailsRepository;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.common.PageResponseDto;
import org.hl.wirtualnyregalbackend.genre.dao.GenreRepository;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.review.book_review.dao.BookReviewRepository;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;


@Service
public class BookService {

    private final ApplicationEventPublisher eventPublisher;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
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
        this.genreRepository = genreRepository;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    public BookDto updateBook(Long bookId, BookDto bookDto) {
        Book book = bookRepository.findById(bookId);
        updateBookEditions(book.getEditions(), bookDto.editions());

        return null;
    }

    private void updateBookEditions(List<BookEdition> bookEditions, List<BookEditionDto> bookEditionDtos) {
        if (bookEditionDtos == null || bookEditionDtos.isEmpty()) return;

        bookEditionDtos.forEach(editionDto -> {
            BookEdition edition = bookEditions.stream()
                .filter(e -> e.getId().equals(editionDto.id()))
                .findFirst()
                .orElseThrow();

            edition.updateIsbn(editionDto.isbn());
            edition.updateTitle(editionDto.title());
            edition.updateDescription(editionDto.description());
            edition.updateLanguage(editionDto.language());
            edition.updateNumberOfPages(editionDto.numberOfPages());
            edition.updatePublicationYear(editionDto.publicationYear());
            // TODO find book format by id and publishers by ids

        });

    }

    public PageResponseDto<BookSearchResponseDto> searchBooks(String query, Pageable pageable) {
        Page<BookEdition> editionPage = bookRepository.findByQuery(query, pageable);
        Page<BookSearchResponseDto> responsePage = editionPage.map(BookMapper::toBookSearchResponseDto);
        return new PageResponseDto<>(responsePage, "books");
    }

    public BookDto findBookById(Long bookId, User user) {
        Book book = bookRepository.findById(bookId);
        BookFoundEvent event = new BookFoundEvent(book, user);
        eventPublisher.publishEvent(event);

        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookDto(book, locale);
    }

}
