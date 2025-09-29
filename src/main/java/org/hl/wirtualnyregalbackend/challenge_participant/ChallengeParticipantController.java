package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantCreateRequest;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantPageResponse;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/challenge-participants")
@AllArgsConstructor
class ChallengeParticipantController {

    private final ChallengeParticipantCommandService command;
    private final ChallengeParticipantQueryService query;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChallengeParticipantResponse createChallengeParticipant(ChallengeParticipantCreateRequest request) {
        return command.createChallengeParticipant(request);
    }

    @DeleteMapping("/{participantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChallengeParticipant(@PathVariable Long participantId) {
        command.deleteParticipantById(participantId);
    }


    @GetMapping
    public ChallengeParticipantPageResponse findChallengeParticipants(@RequestParam Long challengeId, Pageable pageable) {
        return query.findParticipantsByChallengeId(challengeId, pageable);
    }

}
