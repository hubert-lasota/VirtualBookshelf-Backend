package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book.model.BookReadingStatus;
import org.hl.wirtualnyregalbackend.book.model.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookEditionResponseDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookReadingDetailsResponse;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.book.model.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book.model.entity.BookSeries;
import org.hl.wirtualnyregalbackend.book_reading.model.BookReadingDetails;
import org.hl.wirtualnyregalbackend.common.localization.LocalizationUtils;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;
import org.hl.wirtualnyregalbackend.publisher.PublisherMapper;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;

import java.util.List;
import java.util.Locale;


public class BookMapper {

    private BookMapper() { }


    public static BookResponseDto toBookResponseDto(Book book, Locale locale) {
        List<BookEditionResponseDto> editions = book.getEditions()
            .stream()
            .map((edition) -> toBookEditionResponseDto(edition, locale))
            .toList();

        List<AuthorDto> authors = book.getAuthors()
            .stream()
            .map(AuthorMapper::toAuthorDto)
            .toList();

        List<GenreDto> genres = book.getGenres()
            .stream()
            .map(genre -> GenreMapper.toGenreDto(genre, locale))
            .toList();

        List<BookSeriesDto> series = book.getSeries()
            .stream()
            .map((bookSeries) -> toBookSeriesDto(bookSeries, locale))
            .toList();


        return new BookResponseDto(
            book.getId(),
            editions,
            authors,
            genres,
            series,
            book.getOrderInSeries(),
            book.getCover().getCoverUrl()
        );
    }


    public static BookEditionResponseDto toBookEditionResponseDto(BookEdition bookEdition, Locale locale) {
        List<PublisherDto> publishers = bookEdition.getPublishers()
            .stream()
            .map(PublisherMapper::toPublisherDto)
            .toList();

        BookFormatDto format = toBookFormatDto(bookEdition.getFormat(), locale);

        return new BookEditionResponseDto(
            bookEdition.getId(),
            bookEdition.getIsbn(),
            bookEdition.getTitle(),
            bookEdition.getPublicationYear(),
            bookEdition.getNumberOfPages(),
            publishers,
            format
        );
    }

    public static BookFormatDto toBookFormatDto(BookFormat bookFormat, Locale locale) {
        String localizedName = LocalizationUtils.getLocalizedName(bookFormat.getNames(), locale);
        return new BookFormatDto(bookFormat.getId(), localizedName);
    }


    public static BookSeriesDto toBookSeriesDto(BookSeries bookSeries, Locale locale) {
        String localizedName = LocalizationUtils.getLocalizedName(bookSeries.getNames(), locale);
        return new BookSeriesDto(bookSeries.getId(), localizedName);
    }

//    public static BookDetailsResponseDto toBookDetailsResponse(Book book,
//                                                               Float ratingAverage,
//                                                               Page<Tag> tagPage,
//                                                               Page<BookReview> ratingPage,
//                                                               BookReadingDetails readingDetails,
//                                                               Collection<Bookshelf> bookshelves) {
//        Collection<String> tags = book.getTags()
//                .stream()
//                .map(Tag::getName)
//                .toList();
//
//        Long ratingTotal = ratingPage.getTotalElements();
//
//        List<BookshelfHeaderResponse> bookshelvesResponse = bookshelves.stream()
//                .map(BookshelfMapper::toBookshelfHeaderResponse)
//                .toList();
//
//        Page<BookRatingResponseDto> ratingPageResponse = ratingPage.map(BookMapper::toBookRatingResponse);
//
//        return new BookDetailsResponseDto(
//                toBookResponseDto(book),
//                tags,
//                ratingAverage,
//                ratingTotal,
//                ratingPageResponse,
//                toBookReadingDetailsResponse(readingDetails),
//                bookshelvesResponse
//        );
//    }


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
