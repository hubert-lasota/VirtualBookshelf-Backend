package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantCreateRequest;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/challenge-participants")
@AllArgsConstructor
class ChallengeParticipantController {

    private final ChallengeParticipantService participantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChallengeParticipantResponse createChallengeParticipant(ChallengeParticipantCreateRequest request) {
        return participantService.createChallengeParticipant(request);
    }

    @DeleteMapping("/{participantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChallengeParticipant(@PathVariable Long participantId) {
        participantService.deleteParticipantById(participantId);
    }

}
