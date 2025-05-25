package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.book.model.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.*;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesMapper;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesService;
import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.response_model.PageResponseDto;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.genre.GenreService;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.security.model.User;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final ApplicationEventPublisher eventPublisher;
    private final BookRepository bookRepository;
    private final BookFormatRepository bookFormatRepository;
    private final BookSeriesService bookSeriesService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;


    BookService(ApplicationEventPublisher eventPublisher,
                BookRepository bookRepository,
                BookFormatRepository bookFormatRepository,
                BookSeriesService bookSeriesService,
                GenreService genreService,
                AuthorService authorService,
                PublisherService publisherService) {
        this.eventPublisher = eventPublisher;
        this.bookRepository = bookRepository;
        this.bookFormatRepository = bookFormatRepository;
        this.bookSeriesService = bookSeriesService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    public BookResponseDto createBook(BookMutationDto bookMutationDto, MultipartFile coverFile) {
        Set<Author> authors = findOrCreateAuthors(bookMutationDto.authors());
        Set<Genre> genres = findGenres(bookMutationDto);
        List<BookSeriesBook> series = findOrCreateSeries(bookMutationDto.series());
        BookCover cover = getBookCover(bookMutationDto.coverUrl(), coverFile);
        BookFormat format = findBookFormatById(bookMutationDto.format().id());
        Publisher publisher = publisherService.findOrCreatePublisher(bookMutationDto.publisher());

        Book book = BookMapper.toBook(bookMutationDto, cover, format, publisher, authors, genres, series);
        bookRepository.save(book);
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }

    public PageResponseDto<BookResponseDto> searchBooks(String query, Pageable pageable) {
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
                                      BookMutationDto bookMutationDto,
                                      MultipartFile coverFile) {
        Book book = findBookById(bookId);
        book.setIsbnIfNotNull(bookMutationDto.isbn());
        book.setTitleIfNotNull(bookMutationDto.title());
        book.setDescriptionIfNotNull(bookMutationDto.description());
        book.setLanguageIfNotNull(bookMutationDto.language());
        book.setPageCountIfNotNull(bookMutationDto.pageCount());
        book.setPublicationYearIfNotNull(bookMutationDto.publicationYear());

        BookFormatDto formatDto = bookMutationDto.format();
        if (formatDto != null) {
            BookFormat format = findBookFormatById(formatDto.id());
            book.setFormatIfNotNull(format);
        }

        if (bookMutationDto.publisher() != null) {
            Publisher publisher = publisherService.findOrCreatePublisher(bookMutationDto.publisher());
            book.setPublisherIfNotNull(publisher);
        }

        if (bookMutationDto.authors() != null) {
            Set<Author> authors = findOrCreateAuthors(bookMutationDto.authors());
            book.setAuthorsIfNotNull(authors);
        }

        if (bookMutationDto.genres() != null) {
            Set<Genre> genres = findGenres(bookMutationDto);
            book.setGenresIfNotNull(genres);
        }

        if (bookMutationDto.series() != null) {
            List<BookSeriesBook> bookSeriesBooks = findOrCreateSeries(bookMutationDto.series());
            book.setBookSeriesBooksIfNotNull(bookSeriesBooks);
        }

        BookCover cover = getBookCover(bookMutationDto.coverUrl(), coverFile);
        book.setCoverIfNotNull(cover);

        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }


    public Book findBookById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book with id = '%d' not found.".formatted(id)));
    }

    private Set<Author> findOrCreateAuthors(List<AuthorDto> authorDtos) {
        return authorDtos
            .stream()
            .map(authorService::findOrCreateAuthor)
            .collect(Collectors.toSet());
    }

    private Set<Genre> findGenres(BookMutationDto bookMutationDto) {
        List<Long> genreIds = bookMutationDto.genres()
            .stream()
            .map(GenreDto::id)
            .toList();

        return genreService.findGenresByIds(genreIds);
    }

    private List<BookSeriesBook> findOrCreateSeries(List<BookSeriesDto> bookSeriesDtos) {
        return bookSeriesDtos.stream()
            .map(seriesDto -> {
                BookSeries bookSeries = bookSeriesService.findOrCreateBookSeries(seriesDto);
                return BookSeriesMapper.toBookSeriesBookAssociation(bookSeries, seriesDto);
            })
            .toList();
    }

    // TODO moze niech flushnie ID - zapytać sie czatu jak zwracac url api/book-covers/{id}
    private BookCover getBookCover(String coverUrl, MultipartFile coverFile) {
        if (coverUrl != null) {
            return new BookCover(coverUrl, null);
        } else if (coverFile != null && !coverFile.isEmpty()) {
            try {
                BookCoverBinary binaryCover = new BookCoverBinary(
                    coverFile.getBytes(),
                    coverFile.getContentType(),
                    coverFile.getOriginalFilename()
                );
                return new BookCover(null, binaryCover);
            } catch (IOException exc) {
                log.error("Error while reading cover file.", exc);
                return null;
            }
        } else {
            return null;
        }
    }

    private BookFormat findBookFormatById(Long id) throws EntityNotFoundException {
        return bookFormatRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book format with id = '%d' not found.".formatted(id)));
    }

}
