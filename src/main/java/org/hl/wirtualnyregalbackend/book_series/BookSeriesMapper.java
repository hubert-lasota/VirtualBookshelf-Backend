package org.hl.wirtualnyregalbackend.book_series;

import org.hl.wirtualnyregalbackend.book.dto.BookSeriesAssignmentDto;
import org.hl.wirtualnyregalbackend.book.entity.BookSeriesBook;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesMutationDto;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesResponseDto;
import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslationUtils;

import java.util.Locale;

public class BookSeriesMapper {

    private BookSeriesMapper() {
    }

    public static BookSeriesMutationDto toBookSeriesMutationDto(BookSeriesBook bookSeriesAssociation) {
        return new BookSeriesMutationDto(bookSeriesAssociation.getBookSeries().getName());
    }

    public static BookSeriesMutationDto toBookSeriesMutationDto(BookSeries bookSeries) {
        return new BookSeriesMutationDto(bookSeries.getName());
    }

    public static BookSeriesResponseDto toBookSeriesResponseDto(BookSeries bookSeries) {
        BookSeriesMutationDto dto = toBookSeriesMutationDto(bookSeries);
        return new BookSeriesResponseDto(
            bookSeries.getId(),
            dto,
            bookSeries.getCreatedAt(),
            bookSeries.getUpdatedAt()
        );
    }

    public static BookSeries toBookSeries(BookSeriesMutationDto bookSeriesDto) {
        return new BookSeries(bookSeriesDto.name());
    }

    public static BookSeriesBook toBookSeriesBookAssociation(BookSeries bookSeries,
                                                             BookSeriesAssignmentDto bookSeriesDto) {
        return new BookSeriesBook(bookSeries, bookSeriesDto.getBookOrder());
    }

}
