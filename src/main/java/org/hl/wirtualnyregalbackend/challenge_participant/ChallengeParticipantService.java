package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.challenge.ChallengeHelper;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantCreateRequest;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantResponse;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.challenge_participant.event.ChallengeParticipantCreatedEvent;
import org.hl.wirtualnyregalbackend.challenge_participant.event.ChallengeParticipantDeletedEvent;
import org.hl.wirtualnyregalbackend.challenge_participant.exception.ChallengeParticipantNotFoundException;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ChallengeParticipantService {

    private final ChallengeParticipantRepository participantRepository;
    private final ChallengeHelper challengeHelper;
    private final Clock clock;
    private final ApplicationEventPublisher eventPublisher;

    public ChallengeParticipantResponse createChallengeParticipant(ChallengeParticipantCreateRequest participantRequest) {
        Challenge challenge = challengeHelper.findChallengeById(participantRequest.challengeId());
        ChallengeParticipant participant = createChallengeParticipant(challenge);
        return ChallengeParticipantMapper.toChallengeParticipantResponse(participant);
    }

    @Transactional
    public ChallengeParticipant createChallengeParticipant(Challenge challenge) {
        var durationRange = new ChallengeParticipantDurationRange(Instant.now(clock), null);
        ChallengeParticipant participant = new ChallengeParticipant(
            0,
            ChallengeParticipantStatus.ACTIVE,
            durationRange,
            challenge,
            challenge.getUser()
        );
        participant = participantRepository.save(participant);
        log.info("Created participant: {}", participant);
        eventPublisher.publishEvent(ChallengeParticipantCreatedEvent.from(participant));
        return participant;
    }

    @Transactional
    public void deleteParticipantById(Long participantId) {
        ChallengeParticipant participant = findParticipantById(participantId);
        deleteParticipant(participant);
    }

    @Transactional
    public void deleteParticipant(ChallengeParticipant participant) {
        log.info("Deleting participant: {}", participant);
        eventPublisher.publishEvent(ChallengeParticipantDeletedEvent.from(participant));
        participantRepository.delete(participant);
    }


    public ChallengeParticipant findParticipantByChallengeIdAndUserId(Long challengeId, Long userId)
        throws ChallengeParticipantNotFoundException {
        return participantRepository.findByChallengeIdAndUserId(challengeId, userId)
            .orElseThrow(() -> {
                String message = "ChallengeParticipant not found with challengeId: %s and userId: %s".formatted(challengeId, userId);
                log.warn(message);
                return new ChallengeParticipantNotFoundException(message);
            });
    }


    public ChallengeParticipant findParticipantById(Long participantId) throws ChallengeParticipantNotFoundException {
        Optional<ChallengeParticipant> participantOpt = participantId != null ? participantRepository.findById(participantId) : Optional.empty();
        return participantOpt.orElseThrow(() -> {
            log.warn("ChallengeParticipant not found with ID: {}", participantId);
            return new ChallengeParticipantNotFoundException(participantId);
        });
    }

}
