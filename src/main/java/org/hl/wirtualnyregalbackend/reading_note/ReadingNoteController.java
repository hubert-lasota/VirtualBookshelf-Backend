package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_note.dto.NoteWithReadingBookIdDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteMutationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/v1/reading-notes")
@AllArgsConstructor
// TODO add preauthorize
class ReadingNoteController {

    private final ReadingNoteService noteService;


    @PostMapping
    public ResponseEntity<?> createReadingNote(
        @Validated(CreateGroup.class)
        @RequestBody
        NoteWithReadingBookIdDto body,
        UriComponentsBuilder uriBuilder
    ) {
        Long bookshelfBookId = body.getReadingBookId();
        ReadingNoteMutationDto noteDto = body.getNoteDto();
        var response = noteService.createReadingNote(bookshelfBookId, noteDto);

        URI location = uriBuilder
            .path("/v1/reading-notes/{noteId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<?> findReadingNote(@PathVariable Long noteId) {
        return ResponseEntity.ok(noteService.findReadingNoteById(noteId));
    }

    @GetMapping
    public ResponseEntity<?> findReadingNotes(@RequestParam Long readingBookId) {
        var response = noteService.findReadingNotes(readingBookId);
        Map<String, Object> responseMap = Map.of("notes", response);
        return ResponseEntity.ok(responseMap);
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<?> updateReadingNote(@PathVariable Long noteId, @Validated(UpdateGroup.class) @RequestBody ReadingNoteMutationDto noteDto) {
        var response = noteService.updateReadingNote(noteId, noteDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteReadingNote(@PathVariable Long noteId) {
        noteService.deleteReadingNoteById(noteId);
        return ResponseEntity.noContent().build();
    }

}
