package org.hl.wirtualnyregalbackend.bookshelf_book_note;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book.BookshelfBookService;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.dto.BookshelfBookNoteMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.dto.BookshelfBookNoteResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.entity.BookshelfBookNote;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookshelfBookNoteService {

    private final BookshelfBookNoteRepository noteRepository;
    private final BookshelfBookService bookshelfBookService;

    public BookshelfBookNoteResponseDto createBookshelfBookNote(Long bookshelfBookId, BookshelfBookNoteMutationDto noteDto) {
        BookshelfBook book = bookshelfBookService.findBookshelfBookEntityId(bookshelfBookId);
        BookshelfBookNote note = BookshelfBookNoteMapper.toBookshelfBookNote(noteDto, book);
        noteRepository.save(note);
        return BookshelfBookNoteMapper.toBookshelfBookNoteResponseDto(note);
    }

    public BookshelfBookNoteResponseDto updateBookshelfBookNote(Long noteId, BookshelfBookNoteMutationDto noteDto) {
        BookshelfBookNote note = findNoteEntityById(noteId);

        Integer startPage = noteDto.startPage() == null
            ? note.getStartPage()
            : noteDto.startPage();
        Integer endPage = noteDto.endPage() == null
            ? note.getEndPage()
            : noteDto.endPage();

        if (startPage > endPage) {
            throw new InvalidRequestException("Start page cannot be greater than end page.");
        }
        note.setStartPage(startPage);
        note.setEndPage(endPage);

        String title = noteDto.title();
        if (title != null) {
            note.setTitle(title);
        }

        String content = noteDto.content();
        if (content != null) {
            note.setContent(content);
        }


        noteRepository.save(note);
        return BookshelfBookNoteMapper.toBookshelfBookNoteResponseDto(note);
    }

    public BookshelfBookNoteResponseDto findBookshelfBookNoteById(Long noteId) {
        BookshelfBookNote note = findNoteEntityById(noteId);
        return BookshelfBookNoteMapper.toBookshelfBookNoteResponseDto(note);
    }

    public List<BookshelfBookNoteResponseDto> findBookshelfBookNotes(Long bookshelfBookId) {
        List<BookshelfBookNote> notes = noteRepository.findBookshelfBookNotesByBookshelfBookId(bookshelfBookId);
        return notes
            .stream()
            .map(BookshelfBookNoteMapper::toBookshelfBookNoteResponseDto)
            .toList();
    }

    public void deleteBookshelfBookNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    private BookshelfBookNote findNoteEntityById(Long noteId) {
        Optional<BookshelfBookNote> noteOpt = noteId == null
            ? Optional.empty()
            : noteRepository.findById(noteId);
        return noteOpt.orElseThrow(() -> new EntityNotFoundException("Note with id='%d' not found".formatted(noteId)));
    }

}
