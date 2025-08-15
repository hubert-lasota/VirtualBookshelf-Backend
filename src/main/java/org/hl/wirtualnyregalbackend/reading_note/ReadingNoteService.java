package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteCreateDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponseDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponseDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteUpdateDto;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ReadingNoteService {

    private final ReadingNoteRepository noteRepository;
    private final ReadingBookHelper readingBookHelper;

    public ReadingNoteResponseDto createReadingNote(ReadingNoteCreateDto noteDto) {
        ReadingBook book = readingBookHelper.findReadingBookEntityById(noteDto.getReadingBookId());
        ReadingNote note = ReadingNoteMapper.toReadingNote(noteDto, book);
        noteRepository.save(note);
        return ReadingNoteMapper.toReadingNoteResponseDto(note);
    }

    public ReadingNoteResponseDto updateReadingNote(Long noteId, ReadingNoteUpdateDto noteDto) {
        ReadingNote note = findReadingNoteEntityById(noteId);

        Integer dtoPageFrom = noteDto.getPageFrom();
        Integer pageFrom = dtoPageFrom == null
            ? note.getPageFrom()
            : dtoPageFrom;

        Integer dtoPageTo = noteDto.getPageTo();
        Integer pageTo = dtoPageTo == null
            ? note.getPageTo()
            : dtoPageTo;

        note.setPageRange(pageFrom, pageTo);

        String title = noteDto.getTitle();
        if (title != null) {
            note.setTitle(title);
        }

        String content = noteDto.getContent();
        if (content != null) {
            note.setContent(content);
        }

        noteRepository.save(note);
        return ReadingNoteMapper.toReadingNoteResponseDto(note);
    }

    public ReadingNoteResponseDto findReadingNoteById(Long noteId) {
        ReadingNote note = findReadingNoteEntityById(noteId);
        return ReadingNoteMapper.toReadingNoteResponseDto(note);
    }

    public ReadingNoteListResponseDto findReadingNotes(Long readingBookId) {
        List<ReadingNoteResponseDto> notes = noteRepository
            .findReadingNotesByReadingBookId(readingBookId)
            .stream()
            .map(ReadingNoteMapper::toReadingNoteResponseDto)
            .toList();

        return new ReadingNoteListResponseDto(notes);
    }

    public void deleteReadingNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    private ReadingNote findReadingNoteEntityById(Long noteId) {
        Optional<ReadingNote> noteOpt = noteId == null
            ? Optional.empty()
            : noteRepository.findById(noteId);
        return noteOpt.orElseThrow(() -> new EntityNotFoundException("Note with id='%d' not found".formatted(noteId)));
    }

}
