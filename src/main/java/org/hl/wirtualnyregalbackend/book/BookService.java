package org.hl.wirtualnyregalbackend.book;

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
import org.hl.wirtualnyregalbackend.book_series.BookSeriesMapper;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesService;
import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;


@Service
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


    BookService(ApplicationEventPublisher eventPublisher,
                BookRepository bookRepository,
                BookFormatService bookFormatService,
                BookCoverService bookCoverService,
                BookSeriesService bookSeriesService,
                GenreService genreService,
                AuthorService authorService,
                PublisherService publisherService) {
        this.eventPublisher = eventPublisher;
        this.bookRepository = bookRepository;
        this.bookFormatService = bookFormatService;
        this.bookCoverService = bookCoverService;
        this.bookSeriesService = bookSeriesService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    public BookResponseDto createBook(BookMutationDto bookMutationDto, MultipartFile coverFile) {
        Book book = createBookEntity(bookMutationDto, coverFile);
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }

    public Book findOrCreateBook(Long id, BookMutationDto bookMutationDto) {
        if (id != null) {
            return findBookById(id);
        }
        return createBookEntity(bookMutationDto, null);
    }

    private Book createBookEntity(BookMutationDto bookMutationDto, MultipartFile coverFile) {
        Set<Author> authors = findOrCreateAuthors(bookMutationDto.getAuthors());
        Set<Genre> genres = findGenres(bookMutationDto);
        List<BookSeriesBook> series = findOrCreateSeries(bookMutationDto.getSeries());
        BookCover cover = bookCoverService.createBookCover(bookMutationDto.getCoverUrl(), coverFile);
        BookFormat format = bookFormatService.findBookFormatById(bookMutationDto.getFormatId());
        PublisherWithIdDto publisherWithIdDto = bookMutationDto.getPublisher();
        Publisher publisher = publisherService.findOrCreatePublisher(publisherWithIdDto.id(), publisherWithIdDto.publisherDto());

        Book book = BookMapper.toBook(bookMutationDto, cover, format, publisher, authors, genres, series);
        bookRepository.save(book);
        return book;
    }

    public PageResponseDto<BookResponseDto> findBooks(String query, Pageable pageable) {
        Specification<Book> spec = Specification
            .where(BookSpecification.titleIgnoreCaseLike(query))
            .or(BookSpecification.authorFullNameIgnoreCaseLike(query))
            .or(BookSpecification.isbnEqual(query));

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        Locale locale = LocaleContextHolder.getLocale();
        Page<BookResponseDto> responsePage = bookPage.map(book -> BookMapper.toBookResponseDto(book, locale));
        return new PageResponseDto<>(responsePage, "books");
    }

    public BookResponseDto findBookById(Long bookId, User user) {
        Book book = findBookById(bookId);
        BookFoundEvent event = new BookFoundEvent(book, user);
        eventPublisher.publishEvent(event);

        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
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
            Publisher publisher = publisherService.findOrCreatePublisher(publisherWithIdDto.id(), publisherWithIdDto.publisherDto());
            book.setPublisher(publisher);
        }

        List<AuthorWithIdDto> authorWithIdDtos = bookDto.getAuthors();
        if (authorWithIdDtos != null) {
            Set<Author> authors = findOrCreateAuthors(authorWithIdDtos);
            book.setAuthors(authors);
        }

        if (bookDto.getGenres() != null) {
            Set<Genre> genres = findGenres(bookDto);
            book.setGenres(genres);
        }

        if (bookDto.getSeries() != null) {
            List<BookSeriesBook> bookSeriesBooks = findOrCreateSeries(bookDto.getSeries());
            book.setBookSeriesBooks(bookSeriesBooks);
        }

        BookCover cover = bookCoverService.createBookCover(bookDto.getCoverUrl(), coverFile);
        book.setCover(cover);

        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }


    public Book findBookById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book with id = '%d' not found.".formatted(id)));
    }

    private Set<Author> findOrCreateAuthors(List<AuthorWithIdDto> authorDtos) {
        return authorDtos
            .stream()
            .map((authorWithIdDto) -> authorService.findOrCreateAuthor(authorWithIdDto.id(), authorWithIdDto.authorDto()))
            .collect(Collectors.toSet());
    }

    private Set<Genre> findGenres(BookMutationDto bookMutationDto) {
        List<Long> genreIds = bookMutationDto.getGenres()
            .stream()
            .map(GenreWithIdDto::id)
            .toList();

        return genreService.findGenresByIds(genreIds);
    }

    private List<BookSeriesBook> findOrCreateSeries(List<BookSeriesAssignmentDto> bookSeriesDtos) {
        return bookSeriesDtos.stream()
            .map(seriesDto -> {
                BookSeries bookSeries = bookSeriesService.findOrCreateBookSeries(seriesDto.id(), seriesDto.bookSeriesDto());
                return BookSeriesMapper.toBookSeriesBookAssociation(bookSeries, seriesDto);
            })
            .toList();
    }

}
