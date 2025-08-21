package org.hl.wirtualnyregalbackend.reading_note;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteCreateRequest;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reading-notes")
@AllArgsConstructor
// TODO add preauthorize
class ReadingNoteController {

    private final ReadingNoteService noteService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingNoteResponse createReadingNote(@Validated(CreateGroup.class)
                                                 @RequestBody
                                                 ReadingNoteCreateRequest noteRequest) {
        return noteService.createReadingNote(noteRequest);
    }


    @GetMapping
    public ReadingNoteListResponse findReadingNotes(@RequestParam Long readingBookId) {
        return noteService.findReadingNotes(readingBookId);
    }

    @PatchMapping("/{noteId}")
    public ReadingNoteResponse updateReadingNote(@PathVariable
                                                 Long noteId,
                                                 @Validated(UpdateGroup.class)
                                                 @RequestBody
                                                 ReadingNoteUpdateRequest noteDto) {
        return noteService.updateReadingNote(noteId, noteDto);
    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingNote(@PathVariable Long noteId) {
        noteService.deleteReadingNoteById(noteId);
    }

}
