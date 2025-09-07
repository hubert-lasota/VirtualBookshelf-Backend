package org.hl.wirtualnyregalbackend.challenge;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.event.ChallengeParticipantCreatedEvent;
import org.hl.wirtualnyregalbackend.challenge_participant.event.ChallengeParticipantDeletedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
class ChallengeEventListener {

    private final ChallengeRepository challengeRepository;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleChallengeParticipantCreatedEvent(ChallengeParticipantCreatedEvent event) {
        Challenge challenge = event.challenge();
        challenge.incrementTotalParticipants();
        challengeRepository.save(challenge);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleChallengeParticipantDeletedEvent(ChallengeParticipantDeletedEvent event) {
        Challenge challenge = event.challenge();
        challenge.decrementTotalParticipants();
        challengeRepository.save(challenge);
    }

}
