package org.hl.wirtualnyregalbackend.reading_note;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteListResponse;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteRequest;
import org.hl.wirtualnyregalbackend.reading_note.dto.ReadingNoteResponse;
import org.hl.wirtualnyregalbackend.reading_note.model.ReadingNoteFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reading-notes")
@AllArgsConstructor
class ReadingNoteController {

    private final ReadingNoteCommandService command;
    private final ReadingNoteQueryService query;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingNoteResponse createReadingNote(@Validated(CreateGroup.class)
                                                 @RequestBody
                                                 ReadingNoteRequest noteRequest) {
        return command.createReadingNote(noteRequest);
    }


    @PatchMapping("/{noteId}")
    @PreAuthorize("hasPermission(#noteId, 'READING_NOTE', 'UPDATE')")
    public ReadingNoteResponse updateReadingNote(@PathVariable
                                                 Long noteId,
                                                 @Validated(UpdateGroup.class)
                                                 @RequestBody
                                                 ReadingNoteRequest noteRequest) {
        return command.updateReadingNote(noteId, noteRequest);
    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission(#noteId, 'READING_NOTE', 'DELETE')")
    public void deleteReadingNote(@PathVariable Long noteId) {
        command.deleteReadingNoteById(noteId);
    }


    @GetMapping
    public ReadingNoteListResponse findReadingNotes(@Valid ReadingNoteFilter filter) {
        return query.findReadingNotes(filter);
    }

}
