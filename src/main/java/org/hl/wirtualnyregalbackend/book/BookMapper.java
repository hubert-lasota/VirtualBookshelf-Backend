package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorMapper;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book.dto.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.entity.BookSeriesBook;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.BookFormatMapper;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.book_series.BookSeriesMapper;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesMutationDto;
import org.hl.wirtualnyregalbackend.genre.GenreMapper;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponseDto;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.PublisherMapper;
import org.hl.wirtualnyregalbackend.publisher.dto.PublisherMutationDto;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;

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
            bookDto.getIsbn(),
            bookDto.getTitle(),
            bookDto.getPublicationYear(),
            bookDto.getLanguage(),
            bookDto.getPageCount(),
            cover,
            format,
            publisher,
            authors,
            genres,
            bookSeriesBooks
        );
    }

    public static BookResponseDto toBookResponseDto(Book book, Locale locale) {
        BookFormat format = book.getFormat();
        BookFormatDto formatDto = format != null ? BookFormatMapper.toBookFormatDto(format, locale) : null;

        Publisher publisher = book.getPublisher();
        PublisherWithIdDto publisherDto = null;
        if (publisher != null) {
            PublisherMutationDto publisherMutationDto = PublisherMapper.toPublisherMutationDto(publisher);
            publisherDto = new PublisherWithIdDto(publisher.getId(), publisherMutationDto);
        }

        List<AuthorWithIdDto> authors = book.getAuthors()
            .stream()
            .map((author) -> {
                AuthorMutationDto dto = AuthorMapper.toAuthorMutationDto(author);
                return new AuthorWithIdDto(author.getId(), dto);
            })
            .toList();

        List<GenreResponseDto> genres = book.getGenres()
            .stream()
            .map(genre -> GenreMapper.toGenreResponseDto(genre, locale))
            .toList();

        List<BookSeriesAssignmentDto> series = book.getBookSeriesBooks()
            .stream()
            .map((bookSeries) -> {
                BookSeriesMutationDto dto = BookSeriesMapper.toBookSeriesMutationDto(bookSeries);
                return new BookSeriesAssignmentDto(bookSeries.getId(), dto, bookSeries.getBookOrder());
            })
            .toList();

        BookCover cover = book.getCover();
        String coverUrl = cover != null ? cover.getUrl() : null;

        return new BookResponseDto(
            book.getIsbn(),
            book.getTitle(),
            book.getPublicationYear(),
            book.getPageCount(),
            book.getLanguage(),
            book.getDescription(),
            coverUrl,
            publisherDto,
            authors,
            genres,
            series,
            book.getId(),
            formatDto,
            book.getCreatedAt(),
            book.getUpdatedAt()
        );
    }

}
