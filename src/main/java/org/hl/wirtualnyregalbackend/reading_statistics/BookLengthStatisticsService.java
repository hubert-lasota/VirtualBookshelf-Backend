package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLength;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class BookLengthStatisticsService {

    private final BookLengthStatisticsRepository bookLenRepository;

    public void updateBookLengthStatistics(User user, Book book, ReadingStatus status) {
        Integer pageCount = book.getPageCount();
        BookLength length = BookLength.fromPageCount(pageCount);
        Optional<BookLengthStatistics> lenStatsOpt = bookLenRepository.findByUserIdAndLength(user.getId(), length);
        BookLengthStatistics lenStats = lenStatsOpt.orElseGet(() -> new BookLengthStatistics(length, user));
        lenStats.incrementBookCount();
        if (ReadingStatus.READ.equals(status)) {
            lenStats.incrementReadBookCount();
        }
        bookLenRepository.save(lenStats);
    }

}
