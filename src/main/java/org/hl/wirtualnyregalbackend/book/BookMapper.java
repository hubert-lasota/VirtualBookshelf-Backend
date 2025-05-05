package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookEditionDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookSearchResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.book.model.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesMapper;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.localization.LocalizationUtils;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;
import org.hl.wirtualnyregalbackend.publisher.PublisherMapper;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class BookMapper {

    private BookMapper() {
    }

    public static BookResponseDto toBookResponseDto(Book book, Locale locale) {
        BookMutationDto bookDto = toBookMutationDto(book, locale);
        return new BookResponseDto(book.getId(), bookDto);
    }

    public static BookMutationDto toBookMutationDto(Book book, Locale locale) {
        List<BookEditionDto> editions = book.getEditions()
            .stream()
            .map((edition) -> toBookEditionDto(edition, locale))
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
            .map((bookSeries) -> BookSeriesMapper.toBookSeriesDto(bookSeries, locale))
            .toList();

        return new BookMutationDto(
            editions,
            authors,
            genres,
            series,
            book.getCover().getCoverUrl()
        );
    }

    public static BookEditionDto toBookEditionDto(BookEdition bookEdition, Locale locale) {
        List<PublisherDto> publishers = bookEdition.getPublishers()
            .stream()
            .map(PublisherMapper::toPublisherDto)
            .toList();

        BookFormatDto format = toBookFormatDto(bookEdition.getFormat(), locale);

        return new BookEditionDto(
            bookEdition.getId(),
            bookEdition.getIsbn(),
            bookEdition.getTitle(),
            bookEdition.getPublicationYear(),
            bookEdition.getNumberOfPages(),
            publishers,
            format,
            bookEdition.getLanguage(),
            bookEdition.getDescription()
        );
    }

    public static BookEdition toBookEdition(BookEditionDto bookEditionDto,
                                            Set<Publisher> publishers,
                                            BookFormat format) {
        return new BookEdition(
            bookEditionDto.isbn(),
            bookEditionDto.title(),
            bookEditionDto.publicationYear(),
            bookEditionDto.language(),
            bookEditionDto.numberOfPages(),
            publishers,
            format
        );
    }

    public static BookEdition toBookEdition(BookEditionDto bookEditionDto) {
        return toBookEdition(bookEditionDto, null, null);
    }

    public static BookFormatDto toBookFormatDto(BookFormat bookFormat, Locale locale) {
        String localizedName = LocalizationUtils.getLocalizedName(bookFormat.getNames(), locale);
        return new BookFormatDto(bookFormat.getId(), localizedName);
    }

    public static BookSearchResponseDto toBookSearchResponseDto(BookEdition bookEdition) {
        Book book = bookEdition.getBook();
        Collection<AuthorDto> authors = book.getAuthors()
            .stream()
            .map(AuthorMapper::toAuthorDto)
            .toList();

        return new BookSearchResponseDto(
            book.getId(),
            bookEdition.getTitle(),
            authors,
            book.getCover().getCoverUrl()
        );
    }

}
