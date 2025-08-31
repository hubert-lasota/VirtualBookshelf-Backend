package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.dto.BookDetailsResponse;
import org.hl.wirtualnyregalbackend.book.dto.BookRequest;
import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.BookFormatMapper;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.PublisherMapper;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponse;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.hl.wirtualnyregalbackend.reading_book.dto.BookshelfSummaryResponse;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Set;


public class BookMapper {

    private BookMapper() {
    }


    public static Book toBook(BookRequest bookDto,
                              BookCover cover,
                              BookFormat format,
                              Publisher publisher,
                              Set<Author> authors,
                              Set<Genre> genres) {
        return new Book(
            bookDto.isbn(),
            bookDto.title(),
            bookDto.publicationYear(),
            bookDto.language(),
            bookDto.pageCount(),
            bookDto.description(),
            cover,
            format,
            publisher,
            genres,
            authors
        );
    }

    public static BookResponse toBookResponse(Book book, Locale locale) {
        return new BookResponse(
            book.getId(),
            book.getIsbn(),
            book.getTitle(),
            toAuthorResponseList(book),
            toGenreResponseList(book, locale),
            getCoverUrl(book),
            book.getPageCount()
        );
    }

    public static BookDetailsResponse toBookDetailsResponse(Book book,
                                                            ReviewStatistics reviewStats,
                                                            @Nullable BookReview review,
                                                            Locale locale,
                                                            @Nullable ReadingBook readingBook) {
        BookFormat format = book.getFormat();
        BookFormatDto formatDto = format != null ? BookFormatMapper.toBookFormatDto(format, locale) : null;

        Publisher publisher = book.getPublisher();
        PublisherResponse publisherResponse = book.getPublisher() != null
            ? PublisherMapper.toPublisherResponse(publisher)
            : null;



        ReviewResponse reviewResponse = review != null ? ReviewMapper.toReviewResponse(review) : null;
        BookshelfSummaryResponse bookshelf = null;
        if (readingBook != null) {
            Bookshelf b = readingBook.getBookshelf();
            bookshelf = new BookshelfSummaryResponse(b.getId(), b.getName());
        }
        return new BookDetailsResponse(
            book.getId(),
            book.getIsbn(),
            book.getTitle(),
            toAuthorResponseList(book),
            getCoverUrl(book),
            formatDto,
            toGenreResponseList(book, locale),
            reviewStats,
            reviewResponse,
            publisherResponse,
            book.getPageCount(),
            book.getPublicationYear(),
            book.getLanguage(),
            book.getDescription(),
            bookshelf
        );
    }

    private static String getCoverUrl(Book book) {
        BookCover cover = book.getCover();
        return cover != null ? cover.getUrl() : null;
    }

    private static List<AuthorResponse> toAuthorResponseList(Book book) {
        return book
            .getAuthors()
            .stream()
            .map(AuthorMapper::toAuthorResponse)
            .toList();
    }

    private static List<GenreResponse> toGenreResponseList(Book book, Locale locale) {
        return book
            .getGenres()
            .stream()
            .map(genre -> GenreMapper.toGenreResponse(genre, locale))
            .toList();
    }

}
