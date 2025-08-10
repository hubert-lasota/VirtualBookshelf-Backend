package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionListResponseDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponseDto;
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
    public ReadingSessionResponseDto createReadingSession(@Validated(CreateGroup.class) @RequestBody ReadingSessionCreateDto sessionDto) {
        return sessionService.createReadingSession(sessionDto);
    }

    @GetMapping
    public ReadingSessionListResponseDto findCurrentUserReadingSessions(@AuthenticationPrincipal User user) {
        return sessionService.findReadingSessions(user);
    }

    @PatchMapping("/{sessionId}")
    public ReadingSessionResponseDto updateReadingSession(
        @PathVariable
        Long sessionId,
        @Validated(UpdateGroup.class)
        @RequestBody
        ReadingSessionCreateDto sessionDto
    ) {
        return sessionService.updateReadingSession(sessionId, sessionDto);
    }

    @DeleteMapping("/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingSession(@PathVariable Long sessionId) {
        sessionService.deleteReadingSession(sessionId);
    }

}
