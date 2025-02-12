package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.book.dao.BookGenreRepository;
import org.hl.wirtualnyregalbackend.book.dao.BookRatingRepository;
import org.hl.wirtualnyregalbackend.book.dao.BookReadingDetailsRepository;
import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.book.model.Book;
import org.hl.wirtualnyregalbackend.book.model.BookGenre;
import org.hl.wirtualnyregalbackend.book.model.BookRating;
import org.hl.wirtualnyregalbackend.book.model.BookReadingDetails;
import org.hl.wirtualnyregalbackend.book.model.dto.request.BookRatingRequest;
import org.hl.wirtualnyregalbackend.book.model.dto.request.BookRequest;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookDetailsResponse;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponse;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResultResponse;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfService;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.common.ActionResult;
import org.hl.wirtualnyregalbackend.common.ActionType;
import org.hl.wirtualnyregalbackend.common.ApiError;
import org.hl.wirtualnyregalbackend.common.InvalidRequestException;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookRatingRepository bookRatingRepository;
    private final BookReadingDetailsRepository bookReadingDetailsRepository;
    private final BookGenreRepository bookGenreRepository;
    private final BookClient bookClient;
    private final BookAsyncService bookAsyncService;
    private final BookshelfService bookshelfService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    @Value("${book.client.id-prefix}")
    private String BOOK_CLIENT_ID_PREFIX;

    public BookService(BookRepository bookRepository,
                       BookReadingDetailsRepository bookReadingDetailsRepository,
                       BookGenreRepository bookGenreRepository,
                       BookClient bookClient,
                       BookAsyncService bookAsyncService,
                       BookRatingRepository bookRatingRepository,
                       BookshelfService bookshelfService,
                       AuthorService authorService,
                       PublisherService publisherService) {
        this.bookRepository = bookRepository;
        this.bookReadingDetailsRepository = bookReadingDetailsRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.bookClient = bookClient;
        this.bookAsyncService = bookAsyncService;
        this.bookRatingRepository = bookRatingRepository;
        this.bookshelfService = bookshelfService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    public BookRating addBookRating(String id, User user, BookRatingRequest request) {
        Book book = findBookById(id);
        BookRating ratingEntity = BookMapper.toBookRating(request, user, book);
        return bookRatingRepository.save(ratingEntity);
    }

    public void deleteBookRating(Long bookRatingId) {
        if(bookRatingRepository.existsById(bookRatingId)) {
            bookRatingRepository.deleteById(bookRatingId);
        } else {
            throw new InvalidRequestException(null, ActionType.DELETE, "Cannot delete book rating with id " + bookRatingId);
        }
    }

    public BookResponse patchBook(String bookId, BookRequest bookRequest) {
        Book book = findBookById(bookId, true);
        List<ActionResult> results = new ArrayList<>();
        if(bookRequest.title() != null) {
            ActionResult result = book.updateTitle(bookRequest.title());
            results.add(result);
        }
        if(bookRequest.isbn() != null) {
            ActionResult result = book.updateIsbn(bookRequest.isbn());
            results.add(result);
        }
        if(bookRequest.publishedAt() != null) {
            Instant publishedAt = bookRequest.publishedAt().atStartOfDay(ZoneId.of("UTC")).toInstant();
            ActionResult result = book.updatePublishedAt(publishedAt);
            results.add(result);
        }
        if(bookRequest.publishedYear() != null) {
            ActionResult result = book.updatePublishedYear(bookRequest.publishedYear());
            results.add(result);
        }
        if(bookRequest.description() != null) {
            ActionResult result = book.updateDescription(bookRequest.description());
            results.add(result);
        }
        if(bookRequest.languageTag() != null) {
            ActionResult result = book.updateLanguage(bookRequest.languageTag());
            results.add(result);
        }
        if(bookRequest.authors() != null) {
            Set<Author> authors = authorService.findAndCreateAuthors(bookRequest.authors());
            ActionResult result = book.updateAuthors(authors);
            results.add(result);
        }

        if(bookRequest.genres() != null) {
            Set<BookGenre> genres = findAndCreateBookGenres(bookRequest.genres());
            ActionResult result = book.updateGenres(genres);
            results.add(result);
        }

        if(bookRequest.publishers() != null) {
            Set<Publisher> publishers = publisherService.findAndCreatePublishers(bookRequest.publishers());
            ActionResult result = book.updatePublishers(publishers);
            results.add(result);
        }


        boolean isFailure = results.stream()
                .anyMatch(result -> !result.success());
        if (isFailure) {
            List<ApiError> errors = results.stream()
                    .filter(result -> !result.success())
                    .map(ActionResult::error)
                    .toList();
            throw new InvalidRequestException(errors, ActionType.UPDATE, "PATCH Book Failed.");
        }
        Book response = bookRepository.save(book);
        return BookMapper.toBookResponse(response);
    }

    public Page<BookSearchResultResponse> searchBooks(String query, Pageable pageable) {
        return bookClient.searchBooks(query, pageable).map(BookMapper::toBookSearchResultResponse);
    }

    public BookDetailsResponse findBookDetailsById(String id, User user, Pageable ratingPageable) {
        Book book = findBookById(id, true);
        Long bookId = book.getId();
        Float ratingAverage = bookRatingRepository.getBookRatingAverage(bookId);
        Page<BookRating> ratingPage = bookRatingRepository.findRatingPageByBookId(bookId, ratingPageable);
        List<Bookshelf> bookshelves = bookshelfService.findUserBookshelvesByBookId(bookId, user);
        BookReadingDetails bookReadingDetails = bookReadingDetailsRepository.findByBookIdAndUserId(bookId, user.getId());
        return BookMapper.toBookDetailsResponse(book, ratingAverage, ratingPage, bookReadingDetails, bookshelves);
    }

    public BookResponse findBookResponseById(String id) {
        Book book = findBookById(id);
        return BookMapper.toBookResponse(book);
    }

    public Book findBookById(String id) {
        return findBookById(id, false);
    }

    public Book findBookById(String id, boolean joinAllAssociations) {
        if(id.startsWith(BOOK_CLIENT_ID_PREFIX)) {
            return findBookByExternalApiId(id, joinAllAssociations);
        }

        long appId;
        try {
            appId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BookNotFoundException("Book with id %s not found".formatted(id));
        }
        return findBookById(appId, joinAllAssociations);
    }

    private Book findBookById(Long id, boolean joinAllAssociations) throws BookNotFoundException {
        Book book;
        if(joinAllAssociations) {
            book = bookRepository.findWithAllAssociationsById(id);
        } else {
            book = bookRepository.findById(id);
        }
        return book;
    }

    private Book findBookByExternalApiId(String externalApiId, boolean joinAllAssociations) {
        Book book;
        Optional<Book> bookOpt;
        if(joinAllAssociations) {
            bookOpt = bookRepository.findOptionalWithAllAssociationsByExternalApiId(externalApiId);
        } else {
            bookOpt = bookRepository.findOptionalByExternalApiId(externalApiId);
        }

        if(bookOpt.isPresent()) {
            book = bookOpt.get();
        } else {
            BookResponse bookResponse = bookClient.findBookById(externalApiId);
            book = BookMapper.toBook(bookResponse);
            bookAsyncService.saveBookAsync(book, bookResponse.authors(), bookResponse.publishers());
        }
        return book;
    }

    private Set<BookGenre> findAndCreateBookGenres(Collection<String> bookGenres) {
        List<BookGenre> existingBookGenres = bookGenreRepository.findByNamesIgnoreCase(bookGenres);
        List<String> existingNames = existingBookGenres
                .stream().map(BookGenre::getName)
                .toList();

        List<BookGenre> newBookGenres = bookGenres.stream()
                .filter(name -> !existingNames.contains(name))
                .map(BookGenre::new)
                .toList();

        newBookGenres = bookGenreRepository.saveAll(newBookGenres);

        Set<BookGenre> allBookGenres = new HashSet<>(existingBookGenres);
        allBookGenres.addAll(newBookGenres);
        return allBookGenres;
    }

}
