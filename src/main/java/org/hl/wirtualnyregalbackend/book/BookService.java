package org.hl.wirtualnyregalbackend.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.dto.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.book_cover.BookCoverService;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.BookFormatService;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.hl.wirtualnyregalbackend.genre.GenreService;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final ApplicationEventPublisher eventPublisher;
    private final BookRepository bookRepository;
    private final BookHelper bookHelper;
    private final BookFormatService bookFormatService;
    private final BookCoverService bookCoverService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookReviewService bookReviewService;
    private final ReadingBookHelper readingBookHelper;


    public BookResponse createBook(BookRequest bookRequest, MultipartFile coverFile) {
        Book book = createBookEntity(bookRequest, coverFile);
        return BookMapper.toBookResponse(book);
    }

    public Book createBookEntity(BookRequest bookDto, MultipartFile coverFile) {
        Set<Author> authors = findOrCreateAuthors(bookDto.authors());
        Set<Genre> genres = genreService.findGenresByIds(bookDto.genreIds());
        BookCover cover = null;
        String coverUrl = bookDto.coverUrl();
        if (coverFile != null || coverUrl != null) {
            cover = bookCoverService.createBookCover(bookDto.coverUrl(), coverFile);
        }

        Long formatId = bookDto.formatId();
        BookFormat format = null;
        if (formatId != null) {
            format = bookFormatService.findBookFormatById(formatId);
        }

        PublisherWithIdDto publisherWithIdDto = bookDto.publisher();
        Publisher publisher = null;
        if (publisherWithIdDto != null) {
            publisher = publisherService.findOrCreatePublisher(publisherWithIdDto.getId(), publisherWithIdDto.getPublisherDto());
        }

        Book book = BookMapper.toBook(bookDto, cover, format, publisher, authors, genres);
        bookRepository.save(book);
        return book;
    }

    public BookPageResponse findBooks(String query, Pageable pageable) {
        Specification<Book> spec = Specification
            .where(BookSpecification.titleIgnoreCaseLike(query))
            .or(BookSpecification.authorFullNameIgnoreCaseLike(query))
            .or(BookSpecification.isbnEqual(query));

        Page<BookResponse> bookPage = bookRepository.findAll(spec, pageable)
            .map(BookMapper::toBookResponse);

        return BookPageResponse.from(bookPage);
    }

    public BookDetailsResponse findBookDetailsById(Long bookId, User user) {
        Book book = bookHelper.findBookById(bookId);
        BookFoundEvent event = new BookFoundEvent(book, user);
        eventPublisher.publishEvent(event);
        ReviewStatistics reviewStats = bookReviewService.getBookReviewStatistics(bookId);
        BookReview review = bookReviewService.findOptionalBookReviewById(bookId).orElse(null);
        Locale locale = LocaleContextHolder.getLocale();
        ReadingBook rb = readingBookHelper.findUserReadingBookByBookId(bookId, user);
        return BookMapper.toBookDetailsResponse(book, reviewStats, review, locale, rb);
    }

    @Transactional
    public BookResponse updateBook(Long bookId,
                                   BookRequest bookRequest,
                                   MultipartFile coverFile) {
        Book book = bookHelper.findBookById(bookId);
        String isbn = bookRequest.isbn();
        if (isbn != null) {
            book.setIsbn(isbn);
        }

        String title = bookRequest.title();
        if (title != null) {
            book.setTitle(title);
        }

        String description = bookRequest.description();
        if (description != null) {
            book.setDescription(description);
        }

        Locale language = bookRequest.language();
        if (language != null) {
            book.setLanguage(language);
        }

        Integer pageCount = bookRequest.pageCount();
        if (pageCount != null) {
            book.setPageCount(pageCount);
        }

        Integer publicationYear = bookRequest.publicationYear();
        if (publicationYear != null) {
            book.setPublicationYear(publicationYear);
        }

        Long formatId = bookRequest.formatId();
        if (formatId != null) {
            BookFormat format = bookFormatService.findBookFormatById(formatId);
            book.setFormat(format);
        }

        PublisherWithIdDto publisherWithIdDto = bookRequest.publisher();
        if (publisherWithIdDto != null) {
            Publisher publisher = publisherService.findOrCreatePublisher(publisherWithIdDto.getId(), publisherWithIdDto.getPublisherDto());
            book.setPublisher(publisher);
        }

        List<AuthorWithIdDto> authorWithIdDtos = bookRequest.authors();
        if (authorWithIdDtos != null) {
            Set<Author> authors = findOrCreateAuthors(authorWithIdDtos);
            book.setAuthors(authors);
        }

        List<Long> genreIds = bookRequest.genreIds();
        if (genreIds != null) {
            Set<Genre> genres = genreService.findGenresByIds(genreIds);
            book.setGenres(genres);
        }

        BookCover cover = bookCoverService.createBookCover(bookRequest.coverUrl(), coverFile);
        book.setCover(cover);

        return BookMapper.toBookResponse(book);
    }


    public Optional<Book> findBookOptById(@Nullable Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return bookRepository.findById(id);
    }

    private Set<Author> findOrCreateAuthors(List<AuthorWithIdDto> authorDtos) {
        return authorDtos
            .stream()
            .map((authorWithIdDto) -> authorService.findOrCreateAuthor(authorWithIdDto.getId(), authorWithIdDto.getAuthorRequest()))
            .collect(Collectors.toSet());
    }

}
