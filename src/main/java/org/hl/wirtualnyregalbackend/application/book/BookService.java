package org.hl.wirtualnyregalbackend.application.book;

import org.hl.wirtualnyregalbackend.application.book.exception.BookNotFoundException;
import org.hl.wirtualnyregalbackend.infrastructure.book.BookRatingRepository;
import org.hl.wirtualnyregalbackend.infrastructure.book.BookRepository;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookDetailsResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookRatingRequest;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookSearchResultResponse;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookClient bookClient;
    private final BookAsyncService bookAsyncService;
    private final BookRatingRepository bookRatingRepository;

    @Value("${book.client.id-prefix}")
    private String BOOK_CLIENT_ID_PREFIX;

    public BookService(BookRepository bookRepository, BookClient bookClient, BookAsyncService bookAsyncService, BookRatingRepository bookRatingRepository) {
        this.bookRepository = bookRepository;
        this.bookClient = bookClient;
        this.bookAsyncService = bookAsyncService;
        this.bookRatingRepository = bookRatingRepository;
    }


    public Long addBookRating(String id, User user, BookRatingRequest request) {
        Book book = findBookById(id);
        BookRating ratingEntity = BookRatingMapper.toBookRating(request, user, book);
        return bookRatingRepository.save(ratingEntity).getId();
    }

    public void deleteBookRating(Long bookRatingId) {
        bookRatingRepository.deleteById(bookRatingId);
    }

    public Page<BookSearchResultResponse> searchBooks(String query, Pageable pageable) {
        return bookClient.searchBooks(query, pageable).map(BookMapper::toBookSearchResultResponse);
    }

    public BookDetailsResponse findBookDetailsById(String id) {
        Book book = findBookById(id, true);
        return BookMapper.toBookDetailsResponse(book);
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
            bookAsyncService.saveBookAsync(book, bookResponse.authors());
        }
        return book;
    }

}
