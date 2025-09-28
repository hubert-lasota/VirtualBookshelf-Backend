package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteRequest;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_note.exception.ReadingNoteNotFoundException;
import org.hl.wirtualnyregalbackend.reading_session.dto.NoteInSessionDto;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingNoteService {

    private final ReadingNoteRepository noteRepository;
    private final ReadingBookHelper readingBookHelper;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public ReadingNoteResponse createReadingNote(ReadingNoteRequest noteRequest) {
        ReadingBook book = readingBookHelper.findReadingBookById(noteRequest.readingBookId());
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

        return noteRepository.saveAll(noteEntities);
    }

    @Transactional
    public ReadingNoteResponse updateReadingNote(Long noteId, ReadingNoteRequest noteRequest) {
        ReadingNote note = findReadingNoteById(noteId);
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
        ReadingNote note = findReadingNoteById(noteId);
        noteRepository.delete(note);
        eventPublisher.publishEvent(ReadingNoteDeletedEvent.from(note));
        log.info("Deleted Reading Note: {}", note);
    }

    public ReadingNoteListResponse findReadingNotes(Long readingBookId, @Nullable String query) {
        Specification<ReadingNote> spec = Specification.where(ReadingNoteSpecification.byReadingBookId(readingBookId));
        if (query != null) {
            spec = spec.and(ReadingNoteSpecification.byQuery(query));
        }

        List<ReadingNoteResponse> notes = noteRepository
            .findAll(spec)
            .stream()
            .map(ReadingNoteMapper::toReadingNoteResponse)
            .toList();

        return new ReadingNoteListResponse(notes);
    }

    public boolean isNoteAuthor(Long noteId, User user) {
        return noteRepository.isNoteAuthor(noteId, user.getId());
    }

    private ReadingNote findReadingNoteById(Long noteId) throws ReadingNoteNotFoundException {
        Optional<ReadingNote> noteOpt = noteId == null
            ? Optional.empty()
            : noteRepository.findById(noteId);
        return noteOpt.orElseThrow(() -> {
            log.warn("ReadingNote not found with ID: {}", noteId);
            return new ReadingNoteNotFoundException(noteId);
        });
    }

}
