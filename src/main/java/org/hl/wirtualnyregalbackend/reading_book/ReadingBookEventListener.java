package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;

@Component
@AllArgsConstructor
class ReadingBookEventListener {

    private final ReadingBookRepository readingBookRepository;
    private final ReadingBookHelper readingBookHelper;


    @EventListener
    public void handleReadPagesEvent(ReadPagesEvent event) {
        ReadingBook rb = readingBookHelper.findReadingBookById(event.readingBookId());
        rb.addReadPages(event.readPages());

        Integer currentPage = rb.getCurrentPage();
        SessionReadingDurationRange srdr = event.durationRange();
        Instant startedAt = rb.getDurationRange().getStartedAt() != null
            ? rb.getDurationRange().getStartedAt()
            : srdr.getStartedAt();
        if (currentPage.equals(rb.getBook().getPageCount())) {
            ReadingBookDurationRange rbdr = ReadingBookDurationRange.of(startedAt, srdr.getFinishedAt());
            rb.changeStatus(ReadingStatus.READ, rbdr);
        } else {
            rb.changeStatus(ReadingStatus.READING, ReadingBookDurationRange.of(startedAt, null));
        }

        readingBookRepository.save(rb);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingSessionCreatedEvent(ReadingSessionCreatedEvent event) {
        ReadingBook readingBook = readingBookHelper.findReadingBookById(event.readingBookId());
        readingBook.incrementTotalSessions();
        readingBookRepository.save(readingBook);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingSessionDeletedEvent(ReadingSessionDeletedEvent event) {
        ReadingBook rb = readingBookHelper.findReadingBookById(event.readingBookId());
        rb.decrementTotalSessions();
        readingBookRepository.save(rb);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingNoteCreatedEvent(ReadingNoteCreatedEvent event) {
        ReadingBook rb = readingBookHelper.findReadingBookById(event.readingBookId());
        rb.incrementTotalNotes();
        readingBookRepository.save(rb);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingNoteDeletedEvent(ReadingNoteDeletedEvent event) {
        ReadingBook rb = readingBookHelper.findReadingBookById(event.readingBookId());
        rb.decrementTotalNotes();
        readingBookRepository.save(rb);
    }

}
