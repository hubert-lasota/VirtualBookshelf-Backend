package org.hl.wirtualnyregalbackend.book_series;

import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesMutationDto;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesResponseDto;
import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class BookSeriesService {

    private final BookSeriesRepository bookSeriesRepository;

    BookSeriesService(BookSeriesRepository bookSeriesRepository) {
        this.bookSeriesRepository = bookSeriesRepository;
    }

    public BookSeries findOrCreateBookSeries(Long id, BookSeriesMutationDto bookSeriesDto) {
        if (id != null) {
            return bookSeriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found BookSeries with id = %d".formatted(id)));
        }

        return createBookSeriesEntity(bookSeriesDto);
    }

    public PageResponseDto<BookSeriesResponseDto> findBookSeries(Pageable pageable) {
        Page<BookSeries> bookSeriesPage = bookSeriesRepository.findAll(pageable);
        Locale locale = LocaleContextHolder.getLocale();
        Page<BookSeriesResponseDto> dtoPage = bookSeriesPage.map((bookSeries) -> BookSeriesMapper.toBookSeriesResponseDto(bookSeries, locale));
        return new PageResponseDto<>(dtoPage, "series");
    }

    private BookSeries createBookSeriesEntity(BookSeriesMutationDto bookSeriesDto) {
        Locale locale = LocaleContextHolder.getLocale();
        BookSeries bookSeries = BookSeriesMapper.toBookSeries(bookSeriesDto, locale);
        return bookSeriesRepository.save(bookSeries);
    }

}
