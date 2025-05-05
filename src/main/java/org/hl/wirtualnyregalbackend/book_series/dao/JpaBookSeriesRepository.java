package org.hl.wirtualnyregalbackend.book_series.dao;

import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaBookSeriesRepository implements BookSeriesRepository {

    private final SpringJpaBookSeriesRepository bookSeriesRepository;

    public JpaBookSeriesRepository(SpringJpaBookSeriesRepository bookSeriesRepository) {
        this.bookSeriesRepository = bookSeriesRepository;
    }

    @Override
    public BookSeries save(BookSeries bookSeries) {
        return null;
    }

    @Override
    public BookSeries findById(Long id) {
        return bookSeriesRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Not found book series with id='%d'".formatted(id)));
    }

}


@Repository
interface SpringJpaBookSeriesRepository extends JpaRepository<BookSeries, Long> {

}