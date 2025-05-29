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
import org.hl.wirtualnyregalbackend.common.ServerInfoProvider;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
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
    private final BookCoverRepository bookCoverRepository;
    private final BookSeriesService bookSeriesService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final ServerInfoProvider serverInfoProvider;


    BookService(ApplicationEventPublisher eventPublisher,
                BookRepository bookRepository,
                BookFormatRepository bookFormatRepository,
                BookCoverRepository bookCoverRepository,
                BookSeriesService bookSeriesService,
                GenreService genreService,
                AuthorService authorService,
                PublisherService publisherService,
                ServerInfoProvider serverInfoProvider) {
        this.eventPublisher = eventPublisher;
        this.bookRepository = bookRepository;
        this.bookFormatRepository = bookFormatRepository;
        this.bookCoverRepository = bookCoverRepository;
        this.bookSeriesService = bookSeriesService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.serverInfoProvider = serverInfoProvider;
    }


    public BookResponseDto createBook(BookMutationDto bookMutationDto, MultipartFile coverFile) {
        Set<Author> authors = findOrCreateAuthors(bookMutationDto.authors());
        Set<Genre> genres = findGenres(bookMutationDto);
        List<BookSeriesBook> series = findOrCreateSeries(bookMutationDto.series());
        BookCover cover = createBookCover(bookMutationDto.coverUrl(), coverFile);
        BookFormat format = findBookFormatById(bookMutationDto.format().id());
        Publisher publisher = publisherService.findOrCreatePublisher(bookMutationDto.publisher());

        Book book = BookMapper.toBook(bookMutationDto, cover, format, publisher, authors, genres, series);
        bookRepository.save(book);
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
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
        String isbn = bookDto.isbn();
        if (isbn != null) {
            book.setIsbn(isbn);
        }

        String title = bookDto.title();
        if (title != null) {
            book.setTitle(title);
        }


        String description = bookDto.description();
        if (description != null) {
            book.setDescription(description);
        }

        Locale language = bookDto.language();
        if (language != null) {
            book.setLanguage(language);
        }

        Integer pageCount = bookDto.pageCount();
        if (pageCount != null) {
            book.setPageCount(pageCount);
        }

        Integer publicationYear = bookDto.publicationYear();
        if (publicationYear != null) {
            book.setPublicationYear(publicationYear);
        }

        BookFormatDto formatDto = bookDto.format();
        if (formatDto != null) {
            BookFormat format = findBookFormatById(formatDto.id());
            book.setFormat(format);
        }

        if (bookDto.publisher() != null) {
            Publisher publisher = publisherService.findOrCreatePublisher(bookDto.publisher());
            book.setPublisher(publisher);
        }

        if (bookDto.authors() != null) {
            Set<Author> authors = findOrCreateAuthors(bookDto.authors());
            book.setAuthors(authors);
        }

        if (bookDto.genres() != null) {
            Set<Genre> genres = findGenres(bookDto);
            book.setGenres(genres);
        }

        if (bookDto.series() != null) {
            List<BookSeriesBook> bookSeriesBooks = findOrCreateSeries(bookDto.series());
            book.setBookSeriesBooks(bookSeriesBooks);
        }

        BookCover cover = createBookCover(bookDto.coverUrl(), coverFile);
        book.setCover(cover);

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

    private BookCover createBookCover(String coverUrl, MultipartFile coverFile) {
        if (coverUrl != null) {
            return new BookCover(coverUrl, null);
        } else if (coverFile != null && !coverFile.isEmpty()) {
            try {
                BookCoverBinary binaryCover = new BookCoverBinary(
                    coverFile.getBytes(),
                    coverFile.getContentType(),
                    coverFile.getOriginalFilename()
                );
                BookCover cover = new BookCover(null, binaryCover);
                bookCoverRepository.saveAndFlush(cover);
                String origin = serverInfoProvider.getOrigin();
                String url = origin + "/api/v1/book-covers/" + cover.getId();
                cover.setUrl(url);
                bookCoverRepository.save(cover);
                return cover;
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
