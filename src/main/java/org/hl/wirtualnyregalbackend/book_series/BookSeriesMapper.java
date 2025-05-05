package org.hl.wirtualnyregalbackend.book_series;

import org.hl.wirtualnyregalbackend.book.model.entity.BookSeriesBookAssociation;
import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.hl.wirtualnyregalbackend.common.localization.LocalizationUtils;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedName;

import java.util.Locale;

public class BookSeriesMapper {

    private BookSeriesMapper() {
    }

    public static BookSeriesDto toBookSeriesDto(BookSeriesBookAssociation bookSeriesAssociation, Locale locale) {
        BookSeries bookSeries = bookSeriesAssociation.getBookSeries();
        String localizedName = LocalizationUtils.getLocalizedName(bookSeries.getNames(), locale);
        return new BookSeriesDto(bookSeries.getId(), localizedName, bookSeriesAssociation.getBookOrder());
    }

    public static BookSeries toBookSeries(BookSeriesDto bookSeriesDto, Locale locale) {
        LocalizedName localizedName = new LocalizedName(bookSeriesDto.name(), locale);
        return new BookSeries(localizedName);
    }

    public static BookSeriesBookAssociation toBookSeriesBookAssociation(BookSeries bookSeries, BookSeriesDto bookSeriesDto) {
        return new BookSeriesBookAssociation(bookSeries, bookSeriesDto.bookOrder());
    }

}
