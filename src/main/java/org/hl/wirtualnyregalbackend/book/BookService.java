package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.book.dao.BookEditionRepository;
import org.hl.wirtualnyregalbackend.book.dao.BookFormatRepository;
import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.book.model.dto.BookEditionDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.*;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesMapper;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesService;
import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.PageResponseDto;
import org.hl.wirtualnyregalbackend.genre.GenreService;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final ApplicationEventPublisher eventPublisher;
    private final BookRepository bookRepository;
    private final BookEditionRepository bookEditionRepository;
    private final BookFormatRepository bookFormatRepository;
    private final BookSeriesService bookSeriesService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;


    public BookService(ApplicationEventPublisher eventPublisher,
                       BookRepository bookRepository,
                       BookEditionRepository bookEditionRepository,
                       BookFormatRepository bookFormatRepository,
                       BookSeriesService bookSeriesService,
                       GenreService genreService,
                       AuthorService authorService,
                       PublisherService publisherService) {
        this.eventPublisher = eventPublisher;
        this.bookRepository = bookRepository;
        this.bookEditionRepository = bookEditionRepository;
        this.bookFormatRepository = bookFormatRepository;
        this.bookSeriesService = bookSeriesService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    public BookResponseDto createBook(BookMutationDto bookMutationDto) {
        List<BookEdition> editions = bookMutationDto.editions()
            .stream()
            .map(this::createBookEdition)
            .toList();

        Set<Author> authors = findOrCreateAuthors(bookMutationDto);
        Set<Genre> genres = findGenres(bookMutationDto);
        List<BookSeriesBookAssociation> series = findOrCreateSeries(bookMutationDto.series());
        BookCover cover = new BookCover(bookMutationDto.coverUrl(), null);

        Book book = new Book(cover, editions, series, genres, authors);
        bookRepository.save(book);
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }

    public PageResponseDto<BookSearchResponseDto> searchBooks(String query, Pageable pageable) {
        Page<BookEdition> editionPage = bookEditionRepository.findByQuery(query, pageable);
        Page<BookSearchResponseDto> responsePage = editionPage.map(BookMapper::toBookSearchResponseDto);
        return new PageResponseDto<>(responsePage, "books");
    }

    public BookResponseDto findBookById(Long bookId, User user) {
        Book book = bookRepository.findById(bookId);
        BookFoundEvent event = new BookFoundEvent(book, user);
        eventPublisher.publishEvent(event);

        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }

    public BookEdition findBookEditionEntityById(Long bookEditionId) {
        return bookEditionRepository.findById(bookEditionId);
    }

    @Transactional
    public BookResponseDto updateBook(Long bookId, BookMutationDto bookMutationDto) {
        Book book = bookRepository.findById(bookId);
        if (bookMutationDto.editions() != null) {
            List<BookEdition> editions = bookMutationDto.editions()
                .stream()
                .map(editionDto -> {
                    Optional<BookEdition> editionOpt = book.getEditions()
                        .stream()
                        .filter(e -> e.getId().equals(editionDto.id()))
                        .findFirst();

                    return editionOpt.isPresent()
                        ? updateBookEdition(editionDto, editionOpt.get())
                        : createBookEdition(editionDto);
                })
                .toList();

            book.setEditionsIfNotNull(editions);
        }

        if (bookMutationDto.authors() != null) {
            Set<Author> authors = findOrCreateAuthors(bookMutationDto);
            book.setAuthorsIfNotNull(authors);
        }

        if (bookMutationDto.genres() != null) {
            Set<Genre> genres = findGenres(bookMutationDto);
            book.setGenresIfNotNull(genres);
        }

        if (bookMutationDto.series() != null) {
            List<BookSeriesBookAssociation> bookSeriesBookAssociations = findOrCreateSeries(bookMutationDto.series());
            book.setSeriesIfNotNull(bookSeriesBookAssociations);
        }

        book.setCoverIfNotNull(bookMutationDto.coverUrl());

        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponseDto(book, locale);
    }

    private BookEdition updateBookEdition(BookEditionDto editionDto, BookEdition edition) {
        edition.setIsbnIfNotNull(editionDto.isbn());
        edition.setTitleIfNotNull(editionDto.title());
        edition.setDescriptionIfNotNull(editionDto.description());
        edition.setLanguageIfNotNull(editionDto.language());
        edition.setNumberOfPagesIfNotNull(editionDto.numberOfPages());
        edition.setPublicationYearIfNotNull(editionDto.publicationYear());

        BookFormatDto formatDto = editionDto.format();
        if (formatDto != null) {
            BookFormat format = bookFormatRepository.findById(formatDto.id());
            edition.setFormatIfNotNull(format);
        }

        if (editionDto.publishers() != null) {
            Set<Publisher> publishers = findOrCreatePublishers(editionDto);
            edition.setPublishersIfNotNull(publishers);
        }

        return edition;
    }

    private BookEdition createBookEdition(BookEditionDto editionDto) {
        Set<Publisher> publishers = findOrCreatePublishers(editionDto);
        BookFormat format = bookFormatRepository.findById(editionDto.id());
        return BookMapper.toBookEdition(editionDto, publishers, format);
    }

    private Set<Publisher> findOrCreatePublishers(BookEditionDto editionDto) {
        return editionDto.publishers()
            .stream()
            .map(publisherService::findOrCreatePublisher)
            .collect(Collectors.toSet());
    }

    private Set<Author> findOrCreateAuthors(BookMutationDto bookMutationDto) {
        return bookMutationDto.authors()
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

    private List<BookSeriesBookAssociation> findOrCreateSeries(List<BookSeriesDto> bookSeriesDtos) {
        return bookSeriesDtos.stream()
            .map(seriesDto -> {
                BookSeries bookSeries = bookSeriesService.findOrCreateBookSeries(seriesDto);
                return BookSeriesMapper.toBookSeriesBookAssociation(bookSeries, seriesDto);
            })
            .toList();
    }

}
