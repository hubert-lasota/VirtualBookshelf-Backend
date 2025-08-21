package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteCreateRequest;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteUpdateRequest;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_note.exception.ReadingNoteNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ReadingNoteService {

    private final ReadingNoteRepository noteRepository;
    private final ReadingBookHelper readingBookHelper;

    public ReadingNoteResponse createReadingNote(ReadingNoteCreateRequest noteRequest) {
        ReadingBook book = readingBookHelper.findReadingBookById(noteRequest.getReadingBookId());
        ReadingNote note = ReadingNoteMapper.toReadingNote(noteRequest, book);
        noteRepository.save(note);
        return ReadingNoteMapper.toReadingNoteResponse(note);
    }

    public ReadingNoteResponse updateReadingNote(Long noteId, ReadingNoteUpdateRequest noteRequest) {
        ReadingNote note = findReadingNoteById(noteId);

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

        noteRepository.save(note);
        return ReadingNoteMapper.toReadingNoteResponse(note);
    }

    public ReadingNoteListResponse findReadingNotes(Long readingBookId) {
        List<ReadingNoteResponse> notes = noteRepository
            .findReadingNotesByReadingBookId(readingBookId)
            .stream()
            .map(ReadingNoteMapper::toReadingNoteResponse)
            .toList();

        return new ReadingNoteListResponse(notes);
    }

    public void deleteReadingNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    private ReadingNote findReadingNoteById(Long noteId) throws ReadingNoteNotFoundException {
        Optional<ReadingNote> noteOpt = noteId == null
            ? Optional.empty()
            : noteRepository.findById(noteId);
        return noteOpt.orElseThrow(() -> new ReadingNoteNotFoundException(noteId));
    }

}
