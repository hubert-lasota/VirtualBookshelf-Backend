package org.hl.wirtualnyregalbackend.reading_statistics;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf_book.event.BookshelfBookCreatedEvent;
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
    public void handleBookshelfBookCreatedEvent(BookshelfBookCreatedEvent event) {
        BookshelfBook bookshelfBook = event.bookshelfBook();
        User user = bookshelfBook.getBookshelf().getUser();

        bookLenStatsService.updateBookLengthStatistics(user, bookshelfBook.getBook());
        genreStatsService.updateGenreStatistics(user, bookshelfBook.getBook());

    }


}
