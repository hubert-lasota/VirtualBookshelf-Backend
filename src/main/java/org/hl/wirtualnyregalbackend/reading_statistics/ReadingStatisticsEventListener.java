package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookHelper;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookChangedStatusEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadTodayEvent;
import org.hl.wirtualnyregalbackend.user.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
@Async
class ReadingStatisticsEventListener {

    private final BookLengthStatisticsService bookLenStatsService;
    private final GenreStatisticsService genreStatsService;
    private final UserReadingStatisticsService userStatsService;
    private final BookHelper bookHelper;
    private final UserService userService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingBookCreatedEvent(ReadingBookCreatedEvent event) {
        Long userId = event.userId();
        ReadingStatus status = event.status();
        Book book = bookHelper.findBookById(event.bookId());
        User user = userService.findUserById(userId);
        bookLenStatsService.updateBookLengthStatistics(user, book, status);
        genreStatsService.updateGenreStatistics(user, book, status);
        if (ReadingStatus.READ.equals(status)) {
            userStatsService.incrementReadBookCount(user);
        }
    }

    @EventListener
    public void handleReadPagesEvent(ReadPagesEvent event) {
        User user = userService.findUserById(event.userId());
        userStatsService.updateTotalReadPagesAndMinutes(event.readPages(), event.readMinutes(), user);
    }

    @EventListener
    public void handleReadTodayEvent(ReadTodayEvent event) {
        userStatsService.updateReadingStreak(event.lastReadAt(), event.user());
    }

    @EventListener
    public void handleReadingBookFinishedEvent(ReadingBookChangedStatusEvent event) {
        User user = userService.findUserById(event.userId());
        if (ReadingStatus.READ.equals(event.status()) && !ReadingStatus.READ.equals(event.previousStatus())) {
            userStatsService.incrementReadBookCount(user);
        } else if (!ReadingStatus.READ.equals(event.previousStatus())) {
            userStatsService.decrementReadBookCount(user);
        }

    }

}
