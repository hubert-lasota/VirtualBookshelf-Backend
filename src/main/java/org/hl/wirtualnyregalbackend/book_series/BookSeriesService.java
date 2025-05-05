package org.hl.wirtualnyregalbackend.book_series;

import org.hl.wirtualnyregalbackend.book_series.dao.BookSeriesRepository;
import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;
import org.hl.wirtualnyregalbackend.book_series.model.dto.BookSeriesDto;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class BookSeriesService {

    private final BookSeriesRepository bookSeriesRepository;

    public BookSeriesService(BookSeriesRepository bookSeriesRepository) {
        this.bookSeriesRepository = bookSeriesRepository;
    }

    public BookSeries findOrCreateBookSeries(BookSeriesDto bookSeriesDto) {
        if (bookSeriesDto.id() != null) {
            return bookSeriesRepository.findById(bookSeriesDto.id());
        }

        return createBookSeriesEntity(bookSeriesDto);
    }

    private BookSeries createBookSeriesEntity(BookSeriesDto bookSeriesDto) {
        Locale locale = LocaleContextHolder.getLocale();
        BookSeries bookSeries = BookSeriesMapper.toBookSeries(bookSeriesDto, locale);
        return bookSeriesRepository.save(bookSeries);
    }

}
