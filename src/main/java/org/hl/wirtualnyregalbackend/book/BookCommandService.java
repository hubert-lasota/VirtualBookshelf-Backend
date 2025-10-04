package org.hl.wirtualnyregalbackend.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.author.AuthorCommandService;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.dto.AuthorWithIdDto;
import org.hl.wirtualnyregalbackend.book.dto.BookRequest;
import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.book.dto.PublisherWithIdDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.event.BookGenreUpdatedEvent;
import org.hl.wirtualnyregalbackend.book_cover.BookCoverService;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.BookFormatQueryService;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.genre.GenreQueryService;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.PublisherCommandService;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookCommandService {

    private final BookRepository repository;
    private final BookQueryService bookQuery;
    private final BookFormatQueryService formatService;
    private final BookCoverService coverService;
    private final GenreQueryService genreQuery;
    private final AuthorCommandService authorCommand;
    private final PublisherCommandService publisherCommand;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public BookResponse createBook(BookRequest bookRequest, MultipartFile coverFile) {
        Book book = createBookEntity(bookRequest, coverFile);
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponse(book, locale);
    }

    @Transactional
    public Book createBookEntity(BookRequest bookRequest, MultipartFile coverFile) {
        log.info("Creating book: {}", bookRequest);
        Set<Author> authors = findOrCreateAuthors(bookRequest.authors());
        Set<Genre> genres = genreQuery.findGenresByIds(bookRequest.genreIds());
        BookCover cover = null;
        String coverUrl = bookRequest.coverUrl();
        if (coverFile != null || coverUrl != null) {
            cover = coverService.createBookCover(bookRequest.coverUrl(), coverFile);
        }

        Long formatId = bookRequest.formatId();
        BookFormat format = null;
        if (formatId != null) {
            format = formatService.findBookFormatById(formatId);
        }

        PublisherWithIdDto publisherWithIdDto = bookRequest.publisher();
        Publisher publisher = null;
        if (publisherWithIdDto != null) {
            publisher = publisherCommand.findOrCreatePublisher(publisherWithIdDto.getId(), publisherWithIdDto.getPublisherDto());
        }
        Book book = repository.save(BookMapper.toBook(bookRequest, cover, format, publisher, authors, genres));
        eventPublisher.publishEvent(BookGenreUpdatedEvent.fromCreatedBook(book));
        log.info("Created book: {}", book);
        return book;
    }

    @Transactional
    public BookResponse updateBook(Long bookId,
                                   BookRequest bookRequest,
                                   MultipartFile coverFile) {
        Book book = bookQuery.findBookById(bookId);
        log.info("Updating book: {} by request: {}", book, bookRequest);
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
            BookFormat format = formatService.findBookFormatById(formatId);
            book.setFormat(format);
        }

        PublisherWithIdDto publisherWithIdDto = bookRequest.publisher();
        if (publisherWithIdDto != null) {
            Publisher publisher = publisherCommand.findOrCreatePublisher(publisherWithIdDto.getId(), publisherWithIdDto.getPublisherDto());
            book.setPublisher(publisher);
        }

        List<AuthorWithIdDto> authorWithIdDtos = bookRequest.authors();
        if (authorWithIdDtos != null) {
            Set<Author> authors = findOrCreateAuthors(authorWithIdDtos);
            book.setAuthors(authors);
        }

        List<Long> genreIds = bookRequest.genreIds();
        if (genreIds != null) {
            Set<Genre> genres = genreQuery.findGenresByIds(genreIds);
            eventPublisher.publishEvent(BookGenreUpdatedEvent.fromUpdatedBook(book, genres));
            book.setGenres(genres);
        }

        BookCover cover = coverService.createBookCover(bookRequest.coverUrl(), coverFile);
        book.setCover(cover);

        log.info("Book updated: {}", book);
        Locale locale = LocaleContextHolder.getLocale();
        return BookMapper.toBookResponse(book, locale);
    }

    @Transactional
    public Book findOrCreateBook(Long bookId, BookRequest bookDto, MultipartFile cover) {
        return bookQuery.findBookOptById(bookId)
            .orElseGet(() -> createBookEntity(bookDto, cover));
    }

    private Set<Author> findOrCreateAuthors(List<AuthorWithIdDto> authorDtos) {
        return authorDtos
            .stream()
            .map((authorWithIdDto) -> authorCommand.findOrCreateAuthor(authorWithIdDto.getId(), authorWithIdDto.getAuthorRequest()))
            .collect(Collectors.toSet());
    }

}
