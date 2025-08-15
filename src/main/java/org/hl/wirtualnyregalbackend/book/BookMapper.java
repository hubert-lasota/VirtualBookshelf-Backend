package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.dto.BookDetailsResponseDto;
import org.hl.wirtualnyregalbackend.book.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.BookFormatMapper;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponseDto;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.PublisherMapper;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherResponseDto;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;
import org.hl.wirtualnyregalbackend.reading_book.dto.BookshelfSummaryResponseDto;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Set;


public class BookMapper {

    private BookMapper() {
    }


    public static Book toBook(BookMutationDto bookDto,
                              BookCover cover,
                              BookFormat format,
                              Publisher publisher,
                              Set<Author> authors,
                              Set<Genre> genres) {
        return new Book(
            bookDto.getIsbn(),
            bookDto.getTitle(),
            bookDto.getPublicationYear(),
            bookDto.getLanguage(),
            bookDto.getPageCount(),
            bookDto.getDescription(),
            cover,
            format,
            publisher,
            genres,
            authors
        );
    }

    public static BookResponseDto toBookResponseDto(Book book) {
        List<AuthorResponseDto> authors = book
            .getAuthors()
            .stream()
            .map(AuthorMapper::toAuthorResponseDto)
            .toList();

        return new BookResponseDto(
            book.getId(),
            book.getIsbn(),
            book.getTitle(),
            authors,
            book.getCover() != null ? book.getCover().getUrl() : null
        );
    }

    public static BookDetailsResponseDto toBookDetailsResponseDto(Book book,
                                                                  ReviewStatistics reviewStats,
                                                                  @Nullable BookReview review,
                                                                  Locale locale,
                                                                  @Nullable ReadingBook readingBook) {
        BookFormat format = book.getFormat();
        BookFormatDto formatDto = format != null ? BookFormatMapper.toBookFormatDto(format, locale) : null;

        Publisher publisher = book.getPublisher();
        PublisherResponseDto publisherDto = book.getPublisher() != null
            ? PublisherMapper.toPublisherResponseDto(publisher)
            : null;

        List<AuthorResponseDto> authors = book
            .getAuthors()
            .stream()
            .map(AuthorMapper::toAuthorResponseDto)
            .toList();

        List<GenreResponseDto> genres = book
            .getGenres()
            .stream()
            .map(genre -> GenreMapper.toGenreResponseDto(genre, locale))
            .toList();

        BookCover cover = book.getCover();
        String coverUrl = cover != null ? cover.getUrl() : null;

        ReviewResponseDto reviewDto = review != null ? ReviewMapper.toReviewResponseDto(review) : null;
        BookshelfSummaryResponseDto bookshelf = null;
        if (readingBook != null) {
            Bookshelf b = readingBook.getBookshelf();
            bookshelf = new BookshelfSummaryResponseDto(b.getId(), b.getName());
        }
        return new BookDetailsResponseDto(
            book.getId(),
            book.getIsbn(),
            book.getTitle(),
            authors,
            coverUrl,
            formatDto,
            genres,
            reviewStats,
            reviewDto,
            publisherDto,
            book.getPageCount(),
            book.getPublicationYear(),
            book.getLanguage(),
            book.getDescription(),
            bookshelf
        );
    }

}
