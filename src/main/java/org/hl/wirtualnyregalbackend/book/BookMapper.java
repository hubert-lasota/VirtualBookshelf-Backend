package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookMutationDto;
import org.hl.wirtualnyregalbackend.book.model.dto.BookResponseDto;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.book.model.entity.BookCover;
import org.hl.wirtualnyregalbackend.book.model.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book.model.entity.BookSeriesBook;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesMapper;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.translation.TranslationUtils;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.genre.model.dto.GenreDto;
import org.hl.wirtualnyregalbackend.publisher.PublisherMapper;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.publisher.model.dto.PublisherDto;

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
                              Set<Genre> genres,
                              List<BookSeriesBook> bookSeriesBooks) {
        return new Book(
            bookDto.isbn(),
            bookDto.title(),
            bookDto.publicationYear(),
            bookDto.language(),
            bookDto.pageCount(),
            cover,
            format,
            publisher,
            authors,
            genres,
            bookSeriesBooks
        );
    }

    public static BookResponseDto toBookResponseDto(Book book, Locale locale) {
        BookMutationDto bookDto = toBookMutationDto(book, locale);
        return new BookResponseDto(book.getId(), book.getCreatedAt(), book.getUpdatedAt(), bookDto);
    }

    public static BookMutationDto toBookMutationDto(Book book, Locale locale) {
        BookFormatDto format = toBookFormatDto(book.getFormat(), locale);

        PublisherDto publisher = PublisherMapper.toPublisherDto(book.getPublisher());

        List<AuthorDto> authors = book.getAuthors()
            .stream()
            .map(AuthorMapper::toAuthorDto)
            .toList();

        List<GenreDto> genres = book.getGenres()
            .stream()
            .map(genre -> GenreMapper.toGenreDto(genre, locale))
            .toList();

        List<BookSeriesDto> series = book.getBookSeriesBooks()
            .stream()
            .map((bookSeries) -> BookSeriesMapper.toBookSeriesDto(bookSeries, locale))
            .toList();

        return new BookMutationDto(
            book.getIsbn(),
            book.getTitle(),
            book.getPublicationYear(),
            book.getPageCount(),
            format,
            publisher,
            book.getLanguage(),
            book.getDescription(),
            authors,
            genres,
            series,
            book.getCover().getUrl()
        );
    }

    public static BookFormatDto toBookFormatDto(BookFormat bookFormat, Locale locale) {
        String localizedName = TranslationUtils.getTranslatedName(bookFormat.getTranslations(), locale);
        return new BookFormatDto(bookFormat.getId(), localizedName);
    }

}
