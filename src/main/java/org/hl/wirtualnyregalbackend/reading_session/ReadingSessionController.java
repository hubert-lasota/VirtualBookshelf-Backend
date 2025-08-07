package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/reading-sessions")
@AllArgsConstructor
class ReadingSessionController {

    private final ReadingSessionService sessionService;

    @PostMapping
    public ResponseEntity<?> createReadingSession(@Validated(CreateGroup.class) @RequestBody ReadingSessionCreateDto sessionDto) {
        var response = sessionService.createReadingSession(sessionDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserReadingSessions(@AuthenticationPrincipal User user) {
        var response = sessionService.findReadingSessions(user);
        Map<String, Object> responseMap = Map.of("sessions", response);
        return ResponseEntity.ok(responseMap);
    }

    @PatchMapping("/{sessionId}")
    public ResponseEntity<?> updateReadingSession(
        @PathVariable
        Long sessionId,
        @Validated(UpdateGroup.class)
        @RequestBody
        ReadingSessionCreateDto sessionDto
    ) {
        return ResponseEntity.ok(sessionService.updateReadingSession(sessionId, sessionDto));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteReadingSession(@PathVariable Long sessionId) {
        sessionService.deleteReadingSession(sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
