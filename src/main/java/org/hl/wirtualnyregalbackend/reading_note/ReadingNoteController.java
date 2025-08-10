package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteCreateDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponseDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponseDto;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/reading-notes")
@AllArgsConstructor
// TODO add preauthorize
class ReadingNoteController {

    private final ReadingNoteService noteService;


    @PostMapping
    public ResponseEntity<ReadingNoteResponseDto> createReadingNote(
        @Validated(CreateGroup.class)
        @RequestBody
        ReadingNoteCreateDto noteDto,
        UriComponentsBuilder uriBuilder
    ) {
        ReadingNoteResponseDto response = noteService.createReadingNote(noteDto);

        URI location = uriBuilder
            .path("/v1/reading-notes/{noteId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{noteId}")
    public ReadingNoteResponseDto findReadingNote(@PathVariable Long noteId) {
        return noteService.findReadingNoteById(noteId);
    }

    @GetMapping
    public ReadingNoteListResponseDto findReadingNotes(@RequestParam Long readingBookId) {
        return noteService.findReadingNotes(readingBookId);
    }

    @PatchMapping("/{noteId}")
    public ReadingNoteResponseDto updateReadingNote(@PathVariable
                                                    Long noteId,
                                                    @Validated(UpdateGroup.class)
                                                    @RequestBody
                                                    ReadingNoteUpdateDto noteDto) {
        return noteService.updateReadingNote(noteId, noteDto);
    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingNote(@PathVariable Long noteId) {
        noteService.deleteReadingNoteById(noteId);
    }

}
