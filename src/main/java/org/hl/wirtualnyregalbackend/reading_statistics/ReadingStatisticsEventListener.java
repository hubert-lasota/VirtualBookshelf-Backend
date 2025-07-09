package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReadingStatisticsEventListener {

    private final BookLengthStatisticsService bookLenStatsService;
    private final GenreStatisticsService genreStatsService;


    @EventListener
    @Async
    public void handleBookshelfBookCreatedEvent(ReadingBookCreatedEvent event) {
        ReadingBook readingBook = event.readingBook();
        User user = readingBook.getBookshelf().getUser();

        bookLenStatsService.updateBookLengthStatistics(user, readingBook.getBook());
        genreStatsService.updateGenreStatistics(user, readingBook.getBook());

    }


}
