package org.hl.wirtualnyregalbackend.book_series;

import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookSeriesRepository extends JpaRepository<BookSeries, Long> {

}
