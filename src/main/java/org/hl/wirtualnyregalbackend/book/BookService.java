package org.hl.wirtualnyregalbackend.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.dto.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.entity.BookSeriesBook;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.book_cover.BookCoverService;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.BookFormatService;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesMapper;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesService;
import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.hl.wirtualnyregalbackend.common.review.ReviewStats;
import org.hl.wirtualnyregalbackend.genre.GenreService;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.hl.wirtualnyregalbackend.security.entity.User;
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
    private final BookFormatService bookFormatService;
    private final BookCoverService bookCoverService;
    private final BookSeriesService bookSeriesService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookReviewService bookReviewService;


    public BookResponseDto createBook(BookMutationDto bookMutationDto, MultipartFile coverFile) {
        Book book = createBookEntity(bookMutationDto, coverFile);
        return mapToBookResponseDto(book, null);
    }

    public Book createBookEntity(BookMutationDto bookDto, MultipartFile coverFile) {
        Set<Author> authors = findOrCreateAuthors(bookDto.getAuthors());
        Set<Genre> genres = genreService.findGenresByIds(bookDto.getGenreIds());
        BookCover cover = null;
        String coverUrl = bookDto.getCoverUrl();
        if (coverFile != null || coverUrl != null) {
            cover = bookCoverService.createBookCover(bookDto.getCoverUrl(), coverFile);
        }

        Long formatId = bookDto.getFormatId();
        BookFormat format = null;
        if (formatId != null) {
            format = bookFormatService.findBookFormatById(formatId);
        }

        PublisherWithIdDto publisherWithIdDto = bookDto.getPublisher();
        Publisher publisher = null;
        if (publisherWithIdDto != null) {
            publisher = publisherService.findOrCreatePublisher(publisherWithIdDto.getId(), publisherWithIdDto.getPublisherDto());
        }

        List<BookSeriesAssignmentDto> seriesDtos = bookDto.getSeries();
        List<BookSeriesBook> series = null;
        if (seriesDtos != null) {
            series = findOrCreateSeries(seriesDtos);
        }

        Book book = BookMapper.toBook(bookDto, cover, format, publisher, authors, genres, series);
        bookRepository.save(book);
        return book;
    }

    public PageResponseDto<BookResponseDto> findBooks(String query, Pageable pageable) {
        Specification<Book> spec = Specification
            .where(BookSpecification.titleIgnoreCaseLike(query))
            .or(BookSpecification.authorFullNameIgnoreCaseLike(query))
            .or(BookSpecification.isbnEqual(query));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);

        List<Long> bookIds = bookPage
            .getContent()
            .stream()
            .map(Book::getId)
            .toList();

        List<ReviewStats> reviewStats = bookReviewService.getBookReviewStatsByBookIds(bookIds);
        Page<BookResponseDto> responsePage = bookPage.map(book -> {
            ReviewStats stats = reviewStats
                .stream()
                .filter(s -> book.getId().equals(s.entityId()))
                .findFirst()
                .orElseThrow();

            return mapToBookResponseDto(book, stats);
        });
        return new PageResponseDto<>(responsePage, "books");
    }

    public BookResponseDto findBookById(Long bookId, User user) {
        Book book = findBookById(bookId);
        BookFoundEvent event = new BookFoundEvent(book, user);
        eventPublisher.publishEvent(event);
        return mapToBookResponseDto(book);
    }

    @Transactional
    public BookResponseDto updateBook(Long bookId,
                                      BookMutationDto bookDto,
                                      MultipartFile coverFile) {
        Book book = findBookById(bookId);
        String isbn = bookDto.getIsbn();
        if (isbn != null) {
            book.setIsbn(isbn);
        }

        String title = bookDto.getTitle();
        if (title != null) {
            book.setTitle(title);
        }


        String description = bookDto.getDescription();
        if (description != null) {
            book.setDescription(description);
        }

        Locale language = bookDto.getLanguage();
        if (language != null) {
            book.setLanguage(language);
        }

        Integer pageCount = bookDto.getPageCount();
        if (pageCount != null) {
            book.setPageCount(pageCount);
        }

        Integer publicationYear = bookDto.getPublicationYear();
        if (publicationYear != null) {
            book.setPublicationYear(publicationYear);
        }

        Long formatId = bookDto.getFormatId();
        if (formatId != null) {
            BookFormat format = bookFormatService.findBookFormatById(formatId);
            book.setFormat(format);
        }

        PublisherWithIdDto publisherWithIdDto = bookDto.getPublisher();
        if (publisherWithIdDto != null) {
            Publisher publisher = publisherService.findOrCreatePublisher(publisherWithIdDto.getId(), publisherWithIdDto.getPublisherDto());
            book.setPublisher(publisher);
        }

        List<AuthorWithIdDto> authorWithIdDtos = bookDto.getAuthors();
        if (authorWithIdDtos != null) {
            Set<Author> authors = findOrCreateAuthors(authorWithIdDtos);
            book.setAuthors(authors);
        }

        List<Long> genreIds = bookDto.getGenreIds();
        if (genreIds != null) {
            Set<Genre> genres = genreService.findGenresByIds(genreIds);
            book.setGenres(genres);
        }

        if (bookDto.getSeries() != null) {
            List<BookSeriesBook> bookSeriesBooks = findOrCreateSeries(bookDto.getSeries());
            book.setBookSeriesBooks(bookSeriesBooks);
        }

        BookCover cover = bookCoverService.createBookCover(bookDto.getCoverUrl(), coverFile);
        book.setCover(cover);

        return mapToBookResponseDto(book);
    }


    public Book findBookById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book with id = '%d' not found.".formatted(id)));
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
            .map((authorWithIdDto) -> authorService.findOrCreateAuthor(authorWithIdDto.getId(), authorWithIdDto.getAuthorDto()))
            .collect(Collectors.toSet());
    }

    private List<BookSeriesBook> findOrCreateSeries(List<BookSeriesAssignmentDto> bookSeriesDtos) {
        return bookSeriesDtos
            .stream()
            .map(seriesDto -> {
                BookSeries bookSeries = bookSeriesService.findOrCreateBookSeries(seriesDto.getId(), seriesDto.getBookSeriesDto());
                return BookSeriesMapper.toBookSeriesBookAssociation(bookSeries, seriesDto);
            })
            .toList();
    }

    private BookResponseDto mapToBookResponseDto(Book book) {
        ReviewStats stats = bookReviewService.getBookReviewStats(book.getId());
        return mapToBookResponseDto(book, stats);
    }

    private BookResponseDto mapToBookResponseDto(Book book, ReviewStats stats) {
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, stats, locale);
    }

}
