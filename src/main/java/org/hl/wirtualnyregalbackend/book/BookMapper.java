package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.book.model.*;
import org.hl.wirtualnyregalbackend.book.model.dto.request.BookRatingRequest;
import org.hl.wirtualnyregalbackend.book.model.dto.response.*;
import org.hl.wirtualnyregalbackend.bookshelf.BookshelfMapper;
import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfHeaderResponse;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.hl.wirtualnyregalbackend.tag.Tag;
import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.model.dto.UserHeaderResponse;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;


public class BookMapper {

    private BookMapper() { }


    public static Book toBook(BookResponse bookResponse) {
        return new Book(
                bookResponse.id(),
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


        String id = book.getId() == null ? book.getExternalApiId() : book.getId().toString();

        List<String> publishers = book.getPublishers()
                .stream()
                .map(Publisher::getName)
                .toList();

        List<BookGenreResponse> genres = book.getGenres()
                .stream()
                .map(BookMapper::toBookGenreResponse)
                .toList();

        return new BookResponse(
                id,
                book.getIsbn().getStandardizedIsbn(),
                book.getTitle(),
                authors,
                publishers,
                genres,
                book.getDescription(),
                book.getPublishedAt(),
                book.getPublishedYear(),
                book.getLanguage().getLanguage(),
                book.getNumOfPages(),
                book.getCover().getCoverUrl()
        );
    }

    public static BookDetailsResponse toBookDetailsResponse(Book book, Float ratingAverage,
                                                            Page<BookRating> ratingPage,
                                                            BookReadingDetails readingDetails,
                                                            Collection<Bookshelf> bookshelves) {
        Collection<String> tags = book.getTags()
                .stream()
                .map(Tag::getName)
                .toList();

        Long ratingTotal = ratingPage.getTotalElements();

        List<BookshelfHeaderResponse> bookshelvesResponse = bookshelves.stream()
                .map(BookshelfMapper::toBookshelfHeaderResponse)
                .toList();

        Page<BookRatingResponse> ratingPageResponse = ratingPage.map(BookMapper::toBookRatingResponse);



        return new BookDetailsResponse(
                toBookResponse(book),
                tags,
                ratingAverage,
                ratingTotal,
                ratingPageResponse,
                toBookReadingDetailsResponse(readingDetails),
                bookshelvesResponse
        );
    }

    public static BookRating toBookRating(BookRatingRequest bookRatingRequest, User user, Book book) {
        return new BookRating(
                bookRatingRequest.rating(),
                bookRatingRequest.ratingJustification(),
                user,
                book
        );
    }

    public static BookRatingResponse toBookRatingResponse(BookRating bookRating) {
        UserHeaderResponse user = UserMapper.toUserHeaderResponse(bookRating.getUser());
        return new BookRatingResponse(
                bookRating.getId(),
                user,
                bookRating.getRating(),
                bookRating.getRatingJustification(),
                bookRating.getCreatedAt(),
                bookRating.getUpdatedAt()
        );
    }

    public static BookGenreResponse toBookGenreResponse(BookGenre bookGenre) {
        return new BookGenreResponse(
                bookGenre.getId(),
                bookGenre.getName()
        );
    }

    public static BookReadingDetailsResponse toBookReadingDetailsResponse(BookReadingDetails readingDetails) {
        BookReadingStatus status = readingDetails.getFinishedAt() == null ? BookReadingStatus.READING : BookReadingStatus.FINISHED;
        return new BookReadingDetailsResponse(
                readingDetails.getCurrentPage(),
                readingDetails.getProgressPercentage(),
                status,
                readingDetails.getStartedAt(),
                readingDetails.getFinishedAt()
        );
    }

}
