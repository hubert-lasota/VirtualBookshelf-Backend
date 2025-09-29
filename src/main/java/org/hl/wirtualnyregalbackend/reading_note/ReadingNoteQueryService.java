package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_note.exception.ReadingNoteNotFoundException;
import org.hl.wirtualnyregalbackend.reading_note.model.ReadingNoteFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingNoteQueryService {

    private final ReadingNoteRepository noteRepository;

    ReadingNoteListResponse findReadingNotes(ReadingNoteFilter filter) {
        Specification<ReadingNote> spec = Specification.where(ReadingNoteSpecification.byReadingBookId(filter.readingBookId()));
        if (filter.query() != null) {
            spec = spec.and(ReadingNoteSpecification.byQuery(filter.query()));
        }

        if (filter.pageRange() != null) {
            Integer lte = filter.pageRange().lte();
            Integer gte = filter.pageRange().gte();
            if (lte != null) {
                spec = spec.and(ReadingNoteSpecification.ltePageTo(lte));
            }
            if (gte != null) {
                spec = spec.and(ReadingNoteSpecification.gtePageFrom(gte));
            }
        }

        List<ReadingNoteResponse> notes = noteRepository
            .findAll(spec)
            .stream()
            .map(ReadingNoteMapper::toReadingNoteResponse)
            .toList();

        return new ReadingNoteListResponse(notes);
    }

    public boolean isNoteAuthor(Long noteId, User user) {
        return noteRepository.isAuthor(noteId, user.getId());
    }

    ReadingNote findReadingNoteById(Long noteId) throws ReadingNoteNotFoundException {
        Optional<ReadingNote> noteOpt = noteId == null
            ? Optional.empty()
            : noteRepository.findById(noteId);
        return noteOpt.orElseThrow(() -> {
            log.warn("ReadingNote not found with ID: {}", noteId);
            return new ReadingNoteNotFoundException(noteId);
        });
    }
}
