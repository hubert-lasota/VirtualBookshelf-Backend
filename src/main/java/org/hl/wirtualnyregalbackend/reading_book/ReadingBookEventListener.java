package org.hl.wirtualnyregalbackend.reading_book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ReadingBookEventListener {

    private final ReadingBookRepository readingBookRepository;


    @EventListener
    public void handleReadingSessionCreatedEvent(ReadingSessionCreatedEvent event) {
        ReadingSession session = event.readingSession();
        ReadingBook readingBook = session.getReadingBook();
        readingBook.updateCurrentPage(session.getPageRange().to());
        readingBook.incrementTotalSessions();
        readingBookRepository.save(readingBook);
    }

    @EventListener
    public void handleReadingSessionDeletedEvent(ReadingSessionDeletedEvent event) {
        ReadingSession session = event.readingSession();
        ReadingBook readingBook = session.getReadingBook();
        readingBook.decrementTotalSessions();
        readingBookRepository.save(readingBook);
    }

    @EventListener
    public void handleReadingNoteCreatedEvent(ReadingNoteCreatedEvent event) {
        ReadingBook readingBook = event.readingNote().getReadingBook();
        readingBook.incrementTotalNotes();
        readingBookRepository.save(readingBook);
    }

    @EventListener
    public void handleReadingNoteDeletedEvent(ReadingNoteDeletedEvent event) {
        ReadingBook readingBook = event.readingNote().getReadingBook();
        readingBook.decrementTotalNotes();
        readingBookRepository.save(readingBook);
    }

}
