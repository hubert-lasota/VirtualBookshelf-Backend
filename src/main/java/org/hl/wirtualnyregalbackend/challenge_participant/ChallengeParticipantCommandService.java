package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.ChallengeQueryService;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantCreateRequest;
import org.hl.wirtualnyregalbackend.challenge_participant.dto.ChallengeParticipantResponse;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.challenge_participant.event.ChallengeParticipantCreatedEvent;
import org.hl.wirtualnyregalbackend.challenge_participant.event.ChallengeParticipantDeletedEvent;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ChallengeParticipantCommandService {

    private final ChallengeParticipantRepository repository;
    private final ChallengeParticipantQueryService participantQuery;
    private final ChallengeQueryService challengeQuery;
    private final Clock clock;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ChallengeParticipantResponse createChallengeParticipant(ChallengeParticipantCreateRequest participantRequest, User user) {
        Challenge challenge = challengeQuery.findChallengeById(participantRequest.challengeId());
        ChallengeParticipant participant = createChallengeParticipant(challenge, user);
        return ChallengeParticipantMapper.toChallengeParticipantResponse(participant);
    }

    @Transactional
    public ChallengeParticipant createChallengeParticipant(Challenge challenge, User user) {
        var durationRange = new ChallengeParticipantDurationRange(Instant.now(clock), null);
        ChallengeParticipant participant = new ChallengeParticipant(
            0,
            ChallengeParticipantStatus.ACTIVE,
            durationRange,
            challenge,
            user
        );
        participant = repository.save(participant);
        log.info("Created participant: {}", participant);
        eventPublisher.publishEvent(ChallengeParticipantCreatedEvent.from(participant));
        return participant;
    }

    @Transactional
    public void deleteParticipantById(Long participantId) {
        ChallengeParticipant participant = participantQuery.findParticipantById(participantId);
        deleteParticipant(participant);
    }

    @Transactional
    public void deleteParticipant(ChallengeParticipant participant) {
        log.info("Deleting participant: {}", participant);
        eventPublisher.publishEvent(ChallengeParticipantDeletedEvent.from(participant));
        repository.delete(participant);
    }


}
