package org.hl.wirtualnyregalbackend.reading_session;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionListResponse;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionRequest;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.hl.wirtualnyregalbackend.reading_session.model.ReadingSessionFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reading-sessions")
@AllArgsConstructor
class ReadingSessionController {

    private final ReadingSessionCommandService command;
    private final ReadingSessionQueryService query;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingSessionResponse createReadingSession(@Validated(CreateGroup.class)
                                                       @RequestBody
                                                       ReadingSessionRequest sessionRequest) {
        return command.createReadingSession(sessionRequest);
    }

    @PatchMapping("/{sessionId}")
    public ReadingSessionResponse updateReadingSession(
        @PathVariable
        Long sessionId,
        @Validated(UpdateGroup.class)
        @RequestBody
        ReadingSessionRequest sessionRequest
    ) {
        return command.updateReadingSession(sessionId, sessionRequest);
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingSession(@PathVariable Long sessionId) {
        command.deleteReadingSession(sessionId);
    }

    @GetMapping
    @PreAuthorize("hasPermission(#filter.readingBookId, 'READING_BOOK', 'READ')")
    public ReadingSessionListResponse findReadingSessions(@Valid ReadingSessionFilter filter) {
        return query.findReadingSessions(filter);
    }

}
