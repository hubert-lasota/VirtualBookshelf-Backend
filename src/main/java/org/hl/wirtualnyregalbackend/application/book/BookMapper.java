package org.hl.wirtualnyregalbackend.application.book;

import org.hl.wirtualnyregalbackend.application.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookDetailsResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookRatingResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookSearchResultResponse;

import java.util.List;

public class BookMapper {

    private BookMapper() { }

    public static Book toBook(BookResponse bookResponse) {
        String externalApiId = bookResponse.id().startsWith("OL") ? bookResponse.id() : null;
        return new Book(
                externalApiId,
                bookResponse.isbn(),
                bookResponse.title(),
                bookResponse.coverUrl(),
                bookResponse.publishedAtTimestamp(),
                bookResponse.publishedYear(),
                bookResponse.description(),
                bookResponse.numberOfPages(),
                bookResponse.language()
        );
    }

    public static BookSearchResultResponse toBookSearchResultResponse(BookResponse bookResponse) {
        return new BookSearchResultResponse(
                bookResponse.id(),
                bookResponse.isbn(),
                bookResponse.title(),
                bookResponse.authors(),
                bookResponse.coverUrl()
        );
    }

    public static BookResponse toBookResponse(Book book) {
        List<AuthorResponse> authors = book.getAuthors()
                .stream()
                .map(AuthorMapper::toAuthorResponse)
                .toList();

        return new BookResponse(
                book.getId().toString(),
                book.getIsbn().getStandardizedIsbn(),
                book.getTitle(),
                authors,
                book.getDescription(),
                book.getPublishedAt(),
                book.getPublishedYear(),
                book.getLanguage().getLanguage(),
                book.getNumOfPages(),
                book.getBookCover().getCoverUrl()
        );
    }

    public static BookDetailsResponse toBookDetailsResponse(Book book) {
        List<String> genres = book.getBookGenres()
                .stream()
                .map(BookGenre::getName)
                .toList();

        List<BookRatingResponse> ratings = book.getBookRatings()
                .stream()
                .map(BookRatingMapper::toBookRatingResponse)
                .toList();

        return new BookDetailsResponse(
                toBookResponse(book),
                genres,
                ratings
        );
    }
}
