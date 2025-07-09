package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookService;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteMutationDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponseDto;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ReadingNoteService {

    private final ReadingNoteRepository noteRepository;
    private final ReadingBookService readingBookService;

    public ReadingNoteResponseDto createBookshelfBookNote(Long bookshelfBookId, ReadingNoteMutationDto noteDto) {
        ReadingBook book = readingBookService.findReadingBookEntityId(bookshelfBookId);
        ReadingNote note = ReadingNoteMapper.toBookshelfBookNote(noteDto, book);
        noteRepository.save(note);
        return ReadingNoteMapper.toBookshelfBookNoteResponseDto(note);
    }

    public ReadingNoteResponseDto updateBookshelfBookNote(Long noteId, ReadingNoteMutationDto noteDto) {
        ReadingNote note = findNoteEntityById(noteId);

        Integer pageFrom = noteDto.pageFrom() == null
            ? note.getPageFrom()
            : noteDto.pageFrom();
        Integer pageTo = noteDto.pageTo() == null
            ? note.getPageTo()
            : noteDto.pageTo();

        note.setPageRange(pageFrom, pageTo);

        String title = noteDto.title();
        if (title != null) {
            note.setTitle(title);
        }

        String content = noteDto.content();
        if (content != null) {
            note.setContent(content);
        }

        noteRepository.save(note);
        return ReadingNoteMapper.toBookshelfBookNoteResponseDto(note);
    }

    public ReadingNoteResponseDto findBookshelfBookNoteById(Long noteId) {
        ReadingNote note = findNoteEntityById(noteId);
        return ReadingNoteMapper.toBookshelfBookNoteResponseDto(note);
    }

    public List<ReadingNoteResponseDto> findBookshelfBookNotes(Long bookshelfBookId) {
        List<ReadingNote> notes = noteRepository.findBookshelfBookNotesByBookshelfBookId(bookshelfBookId);
        return notes
            .stream()
            .map(ReadingNoteMapper::toBookshelfBookNoteResponseDto)
            .toList();
    }

    public void deleteBookshelfBookNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    private ReadingNote findNoteEntityById(Long noteId) {
        Optional<ReadingNote> noteOpt = noteId == null
            ? Optional.empty()
            : noteRepository.findById(noteId);
        return noteOpt.orElseThrow(() -> new EntityNotFoundException("Note with id='%d' not found".formatted(noteId)));
    }

}
