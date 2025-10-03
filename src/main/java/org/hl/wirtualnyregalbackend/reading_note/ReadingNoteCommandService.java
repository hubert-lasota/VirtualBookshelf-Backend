package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.common.reading.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookQueryService;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteRequest;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.dto.NoteInSessionDto;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingNoteCommandService {

    private final ReadingNoteRepository repository;
    private final ReadingNoteQueryService noteQuery;
    private final ReadingBookQueryService readingBookQuery;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public ReadingNoteResponse createReadingNote(ReadingNoteRequest noteRequest) {
        ReadingBook book = readingBookQuery.findReadingBookById(noteRequest.readingBookId());
        ReadingNote note = ReadingNoteMapper.toReadingNote(noteRequest, book, null);
        eventPublisher.publishEvent(ReadingNoteCreatedEvent.from(note));
        log.info("Created Reading Note: {}", note);
        return ReadingNoteMapper.toReadingNoteResponse(note);
    }

    @Transactional
    public List<ReadingNote> createReadingNotesInSession(ReadingSession session, List<NoteInSessionDto> notes) {
        List<ReadingNote> noteEntities = notes
            .stream()
            .map((note) -> ReadingNoteMapper.toReadingNote(session, note))
            .toList();

        noteEntities.forEach((note) -> eventPublisher.publishEvent(ReadingNoteCreatedEvent.from(note)));

        return repository.saveAll(noteEntities);
    }

    @Transactional
    public ReadingNoteResponse updateReadingNote(Long noteId, ReadingNoteRequest noteRequest) {
        ReadingNote note = noteQuery.findReadingNoteById(noteId);
        log.info("Updating Reading Note: {} by request: {}", note, noteRequest);

        PageRange pr = PageRange.merge(note.getPageRange(), noteRequest.pageRange());
        note.setPageRange(pr);

        String title = noteRequest.title();
        if (title != null) {
            note.setTitle(title);
        }

        String content = noteRequest.content();
        if (content != null) {
            note.setContent(content);
        }

        log.info("Updated Reading Note: {}", note);
        return ReadingNoteMapper.toReadingNoteResponse(note);
    }

    @Transactional
    public void deleteReadingNoteById(Long noteId) {
        ReadingNote note = noteQuery.findReadingNoteById(noteId);
        repository.delete(note);
        eventPublisher.publishEvent(ReadingNoteDeletedEvent.from(note));
        log.info("Deleted Reading Note: {}", note);
    }

    public void deleteNotesByReadingBookId(Long readingBookId) {
        log.info("Deleting notes for reading book with ID: {}", readingBookId);
        repository.deleteByReadingBookId(readingBookId);
    }

}
