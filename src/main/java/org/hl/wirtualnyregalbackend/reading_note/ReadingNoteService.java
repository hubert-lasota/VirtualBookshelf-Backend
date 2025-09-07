package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteCreateRequest;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteUpdateRequest;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_note.event.ReadingNoteDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_note.exception.ReadingNoteNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
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
    public ReadingNoteResponse createReadingNote(ReadingNoteCreateRequest noteRequest) {
        ReadingBook book = readingBookHelper.findReadingBookById(noteRequest.getReadingBookId());
        ReadingNote note = ReadingNoteMapper.toReadingNote(noteRequest, book);
        eventPublisher.publishEvent(ReadingNoteCreatedEvent.from(note));
        log.info("Created Reading Note: {}", note);
        return ReadingNoteMapper.toReadingNoteResponse(note);
    }

    @Transactional
    public ReadingNoteResponse updateReadingNote(Long noteId, ReadingNoteUpdateRequest noteRequest) {
        ReadingNote note = findReadingNoteById(noteId);
        log.info("Updating Reading Note: {} by request: {}", note, noteRequest);

        PageRange pr = PageRange.merge(note.getPageRange(), noteRequest.getPageRange());
        note.setPageRange(pr);

        String title = noteRequest.getTitle();
        if (title != null) {
            note.setTitle(title);
        }

        String content = noteRequest.getContent();
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

    public ReadingNoteListResponse findReadingNotes(Long readingBookId) {
        List<ReadingNoteResponse> notes = noteRepository
            .findReadingNotesByReadingBookId(readingBookId)
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
