package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLength;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class BookLengthStatisticsService {

    private final BookLengthStatisticsRepository bookLenRepository;
    private final Clock clock;

    public void updateBookLengthStatistics(User user, Book book, ReadingStatus status) {
        BookLength length = BookLength.fromPageCount(book.getPageCount());
        YearMonth yearMonth = YearMonth.now(clock);
        Optional<BookLengthStatistics> lenStatsOpt = bookLenRepository.findByUserAndLengthAndYearMonth(user, length, yearMonth);
        BookLengthStatistics lenStats = lenStatsOpt.orElseGet(() -> new BookLengthStatistics(length, user, yearMonth));
        lenStats.incrementBookCount();
        if (ReadingStatus.READ.equals(status)) {
            lenStats.incrementReadBookCount();
        }
        bookLenRepository.save(lenStats);
    }

    public List<BookLengthStatistics> findBookLengthStatistics(User user) {
        return bookLenRepository.findByUser(user);
    }

}
