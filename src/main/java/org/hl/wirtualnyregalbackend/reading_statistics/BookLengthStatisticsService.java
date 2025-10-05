package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_statistics.dto.BookLengthStatisticsResponse;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.hl.wirtualnyregalbackend.reading_statistics.model.BookLength;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class BookLengthStatisticsService {

    private final BookLengthStatisticsRepository repository;
    private final Clock clock;

    public void updateBookLengthStatistics(User user, Book book, ReadingStatus status) {
        BookLength length = BookLength.fromPageCount(book.getPageCount());
        YearMonth yearMonth = YearMonth.now(clock);
        Optional<BookLengthStatistics> lenStatsOpt = repository.findByUserAndLengthAndYearMonth(user, length, yearMonth);
        BookLengthStatistics lenStats = lenStatsOpt.orElseGet(() -> new BookLengthStatistics(length, user, yearMonth));
        lenStats.incrementBookCount();
        if (ReadingStatus.READ.equals(status)) {
            lenStats.incrementReadBookCount();
        }
        repository.save(lenStats);
    }

    public List<BookLengthStatistics> findBookLengthStatistics(User user, @Nullable Integer year) {
        Specification<BookLengthStatistics> spec = StatisticsSpecification.byUserId(user.getId());
        if (year != null) {
            spec = spec.and(StatisticsSpecification.byYear(year));
        }
        return repository.findAll(spec);
    }

    public List<BookLengthStatisticsResponse> findStatisticsSummaryByUser(User user) {
        return repository.findStatisticsSummaryByUser(user);
    }

}
