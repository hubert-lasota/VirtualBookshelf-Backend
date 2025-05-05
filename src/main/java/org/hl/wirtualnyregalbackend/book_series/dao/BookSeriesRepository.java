package org.hl.wirtualnyregalbackend.book_series.dao;

import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;

public interface BookSeriesRepository {

    BookSeries save(BookSeries bookSeries);

    BookSeries findById(Long id);

}
