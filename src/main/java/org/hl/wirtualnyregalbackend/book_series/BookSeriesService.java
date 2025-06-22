package org.hl.wirtualnyregalbackend.book_series;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesMutationDto;
import org.hl.wirtualnyregalbackend.book_series.dto.BookSeriesResponseDto;
import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookSeriesService {

    private final BookSeriesRepository bookSeriesRepository;


    public BookSeries findOrCreateBookSeries(Long id, BookSeriesMutationDto bookSeriesDto) {
        if (id != null) {
            return bookSeriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found BookSeries with id = %d".formatted(id)));
        }

        return createBookSeriesEntity(bookSeriesDto);
    }

    public PageResponseDto<BookSeriesResponseDto> findBookSeries(Pageable pageable) {
        Page<BookSeries> bookSeriesPage = bookSeriesRepository.findAll(pageable);
        Page<BookSeriesResponseDto> dtoPage = bookSeriesPage.map(BookSeriesMapper::toBookSeriesResponseDto);
        return new PageResponseDto<>(dtoPage, "series");
    }

    private BookSeries createBookSeriesEntity(BookSeriesMutationDto bookSeriesDto) {
        BookSeries bookSeries = BookSeriesMapper.toBookSeries(bookSeriesDto);
        return bookSeriesRepository.save(bookSeries);
    }

}
