package org.hl.wirtualnyregalbackend.recommendation;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.event.BookFoundEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
@Async
class RecommendationEventListener {

    private final RecommendationService recommendationService;


    @EventListener
    public void handleBookFoundEvent(BookFoundEvent event) {
        recommendationService.boostScoresForBook(event.bookId(), event.userId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingSessionCreatedEvent(ReadingSessionCreatedEvent event) {
        recommendationService.boostAuthorsAndGenresScore(event.bookId(), event.userId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingSessionDeletedEvent(ReadingSessionDeletedEvent event) {
        recommendationService.reduceAuthorsAndGenresScore(event.bookId(), event.userId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingBookCreatedEvent(ReadingBookCreatedEvent event) {
        recommendationService.boostScoresForBook(event.bookId(), event.userId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingBookDeletedEvent(ReadingBookDeletedEvent event) {
        recommendationService.reduceScoresForBook(event.bookId(), event.userId());
    }

}
