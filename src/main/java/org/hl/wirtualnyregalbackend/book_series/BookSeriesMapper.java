package org.hl.wirtualnyregalbackend.book_series;

import org.hl.wirtualnyregalbackend.book.model.entity.BookSeriesBook;
import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslationUtils;

import java.util.Locale;

public class BookSeriesMapper {

    private BookSeriesMapper() {
    }

    public static BookSeriesDto toBookSeriesDto(BookSeriesBook bookSeriesAssociation, Locale locale) {
        BookSeries bookSeries = bookSeriesAssociation.getBookSeries();
        String localizedName = TranslationUtils.getTranslatedName(bookSeries.getNames(), locale);
        return new BookSeriesDto(bookSeries.getId(), localizedName, bookSeriesAssociation.getBookOrder());
    }

    public static BookSeries toBookSeries(BookSeriesDto bookSeriesDto, Locale locale) {
        TranslatedName translatedName = new TranslatedName(bookSeriesDto.name(), locale);
        return new BookSeries(translatedName);
    }

    public static BookSeriesBook toBookSeriesBookAssociation(BookSeries bookSeries, BookSeriesDto bookSeriesDto) {
        return new BookSeriesBook(bookSeries, bookSeriesDto.bookOrder());
    }

}
