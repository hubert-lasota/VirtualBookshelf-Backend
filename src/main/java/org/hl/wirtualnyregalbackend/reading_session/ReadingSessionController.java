package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionListResponse;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionRequest;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reading-sessions")
@AllArgsConstructor
class ReadingSessionController {

    private final ReadingSessionService sessionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingSessionResponse createReadingSession(@Validated(CreateGroup.class)
                                                       @RequestBody
                                                       ReadingSessionRequest sessionRequest) {
        return sessionService.createReadingSession(sessionRequest);
    }

    @PatchMapping("/{sessionId}")
    public ReadingSessionResponse updateReadingSession(
        @PathVariable
        Long sessionId,
        @Validated(UpdateGroup.class)
        @RequestBody
        ReadingSessionRequest sessionRequest
    ) {
        return sessionService.updateReadingSession(sessionId, sessionRequest);
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingSession(@PathVariable Long sessionId) {
        sessionService.deleteReadingSession(sessionId);
    }

    @GetMapping
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'READ')")
    public ReadingSessionListResponse findReadingSessions(@RequestParam Long readingBookId) {
        return sessionService.findReadingSessions(readingBookId);
    }

}
