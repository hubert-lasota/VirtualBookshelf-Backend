package org.hl.wirtualnyregalbackend.bookshelf_book_note;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.dto.BookshelfBookNoteMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book_note.dto.NoteWithBookshelfBookIdDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/v1/bookshelf-book-notes")
@AllArgsConstructor
// TODO add preauthorize
class BookshelfBookNoteController {

    private final BookshelfBookNoteService noteService;


    @PostMapping
    public ResponseEntity<?> createBookshelfBookNote(
        @Validated(CreateGroup.class)
        @RequestBody
        NoteWithBookshelfBookIdDto body,
        UriComponentsBuilder uriBuilder
    ) {
        Long bookshelfBookId = body.getBookshelfBookId();
        BookshelfBookNoteMutationDto noteDto = body.getNoteDto();
        var response = noteService.createBookshelfBookNote(bookshelfBookId, noteDto);

        URI location = uriBuilder
            .path("/v1/bookshelf-book-notes/{noteId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<?> findBookshelfBookNote(@PathVariable Long noteId) {
        return ResponseEntity.ok(noteService.findBookshelfBookNoteById(noteId));
    }

    @GetMapping
    public ResponseEntity<?> findBookshelfBookNotes(@RequestParam Long bookshelfBookId) {
        var response = noteService.findBookshelfBookNotes(bookshelfBookId);
        Map<String, Object> responseMap = Map.of("notes", response);
        return ResponseEntity.ok(responseMap);
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<?> updateBookshelfBookNote(@PathVariable Long noteId, @Validated(UpdateGroup.class) @RequestBody BookshelfBookNoteMutationDto noteDto) {
        var response = noteService.updateBookshelfBookNote(noteId, noteDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteBookshelfBookNote(@PathVariable Long noteId) {
        noteService.deleteBookshelfBookNoteById(noteId);
        return ResponseEntity.noContent().build();
    }

}
