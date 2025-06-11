package org.hl.wirtualnyregalbackend.book_series;

import org.hl.wirtualnyregalbackend.book.dto.BookSeriesAssignmentDto;
import org.hl.wirtualnyregalbackend.book.entity.BookSeriesBook;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesMutationDto;
import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslationUtils;

import java.util.Locale;

public class BookSeriesMapper {

    private BookSeriesMapper() {
    }

    public static BookSeriesMutationDto toBookSeriesMutationDto(BookSeriesBook bookSeriesAssociation, Locale locale) {
        BookSeries bookSeries = bookSeriesAssociation.getBookSeries();
        String localizedName = TranslationUtils.getTranslatedName(bookSeries.getNames(), locale);
        return new BookSeriesMutationDto(localizedName);
    }

    public static BookSeries toBookSeries(BookSeriesMutationDto bookSeriesDto, Locale locale) {
        TranslatedName translatedName = new TranslatedName(bookSeriesDto.name(), locale);
        return new BookSeries(translatedName);
    }

    public static BookSeriesBook toBookSeriesBookAssociation(BookSeries bookSeries, BookSeriesAssignmentDto bookSeriesDto) {
        return new BookSeriesBook(bookSeries, bookSeriesDto.bookOrder());
    }

}
