package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateRequest;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionPageResponse;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                                       ReadingSessionCreateRequest sessionRequest) {
        return sessionService.createReadingSession(sessionRequest);
    }

    @GetMapping
    public ReadingSessionPageResponse findCurrentUserReadingSessions(@AuthenticationPrincipal User user,
                                                                     Pageable pageable) {
        return sessionService.findReadingSessions(user, pageable);
    }

    @PatchMapping("/{sessionId}")
    public ReadingSessionResponse updateReadingSession(
        @PathVariable
        Long sessionId,
        @Validated(UpdateGroup.class)
        @RequestBody
        ReadingSessionCreateRequest sessionRequest
    ) {
        return sessionService.updateReadingSession(sessionId, sessionRequest);
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingSession(@PathVariable Long sessionId) {
        sessionService.deleteReadingSession(sessionId);
    }

}
