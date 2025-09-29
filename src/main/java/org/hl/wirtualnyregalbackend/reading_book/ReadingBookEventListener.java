package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;

@Component
@AllArgsConstructor
@Transactional
class ReadingBookEventListener {

    private final ReadingBookQueryService query;


    @EventListener
    public void handleReadPagesEvent(ReadPagesEvent event) {
        ReadingBook rb = query.findReadingBookById(event.readingBookId());
        SessionReadingDurationRange srdr = event.durationRange();
        Instant startedAt = rb.getDurationRange().getStartedAt() != null
            ? rb.getDurationRange().getStartedAt()
            : srdr.getStartedAt();
        ReadingBookDurationRange rbdr = ReadingBookDurationRange.of(startedAt, srdr.getFinishedAt());
        rb.addReadPages(event.readPages(), rbdr);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingSessionCreatedEvent(ReadingSessionCreatedEvent event) {
        ReadingBook readingBook = query.findReadingBookById(event.readingBookId());
        readingBook.incrementTotalSessions();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingSessionDeletedEvent(ReadingSessionDeletedEvent event) {
        ReadingBook rb = query.findReadingBookById(event.readingBookId());
        rb.decrementTotalSessions();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingNoteCreatedEvent(ReadingNoteCreatedEvent event) {
        ReadingBook rb = query.findReadingBookById(event.readingBookId());
        rb.incrementTotalNotes();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingNoteDeletedEvent(ReadingNoteDeletedEvent event) {
        ReadingBook rb = query.findReadingBookById(event.readingBookId());
        rb.decrementTotalNotes();
    }

}
