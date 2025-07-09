package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLength;
import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLengthStatistics;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
class BookLengthStatisticsService {

    private final BookLengthStatisticsRepository bookLenRepository;

    @Transactional
    public void updateBookLengthStatistics(User user, Book book) {
        Integer pageCount = book.getPageCount();
        BookLength length = BookLength.fromPageCount(pageCount);
        Optional<BookLengthStatistics> lenStatsOpt = bookLenRepository.findByUserIdAndLength(user.getId(), length);
        if (lenStatsOpt.isPresent()) {
            BookLengthStatistics lenStats = lenStatsOpt.get();
            lenStats.incrementBookCount();
        } else {
            BookLengthStatistics lenStats = new BookLengthStatistics(length, 1L, user);
            bookLenRepository.save(lenStats);
        }
    }

}
