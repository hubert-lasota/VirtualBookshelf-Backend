package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookFinishedEvent;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadTodayEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Async
public class ReadingStatisticsEventListener {

    private final BookLengthStatisticsService bookLenStatsService;
    private final GenreStatisticsService genreStatsService;
    private final UserReadingStatisticsService userStatsService;

    @EventListener
    public void handleReadingBookCreatedEvent(ReadingBookCreatedEvent event) {
        ReadingBook readingBook = event.readingBook();
        User user = readingBook.getBookshelf().getUser();
        Book book = readingBook.getBook();
        ReadingStatus status = readingBook.getStatus();
        bookLenStatsService.updateBookLengthStatistics(user, book, status);
        genreStatsService.updateGenreStatistics(user, book, status);
        if (ReadingStatus.READ.equals(status)) {
            userStatsService.updateTotalReadBooks(user);
        }
    }

    @EventListener
    public void handleReadPagesEvent(ReadPagesEvent event) {
        User user = event.readingSession().getReadingBook().getBookshelf().getUser();
        userStatsService.updateTotalReadPagesAndMinutes(event.readPages(), event.readMinutes(), user);
    }

    @EventListener
    public void handleReadTodayEvent(ReadTodayEvent event) {
        userStatsService.updateReadingStreak(event.lastReadAt(), event.user());
    }

    @EventListener
    public void handleReadingBookFinishedEvent(ReadingBookFinishedEvent event) {
        User user = event.readingBook().getBookshelf().getUser();
        userStatsService.updateTotalReadBooks(user);
    }

}
