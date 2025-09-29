package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.challenge.ChallengeQueryService;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
@AllArgsConstructor
class ChallengeParticipantScheduler {

    private final ChallengeQueryService challengeQuery;
    private final ChallengeParticipantRepository repository;
    private final Clock clock;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void changeParticipantStatusForEndedChallenges() {
        Instant now = Instant.now(clock);
        LocalDate endAt = LocalDate.ofInstant(now, ZoneId.of("UTC"));
        Slice<Challenge> challenges;
        Pageable pageable = PageRequest.of(0, 1000);
        do {
            challenges = challengeQuery.findChallengesByEndAt(endAt, pageable);
            challenges.forEach(challenge -> findAndChangeParticipantStatus(challenge, now));
            pageable = challenges.nextPageable();
        } while (challenges.hasNext());
    }

    private void findAndChangeParticipantStatus(Challenge challenge, Instant now) {
        Pageable pageable = PageRequest.of(0, 1000);
        Slice<ChallengeParticipant> participants;

        final var duration = ChallengeParticipantDurationRange.of(null, now);
        do {
            participants = repository.findByChallengeAndStatus(challenge, ChallengeParticipantStatus.ACTIVE, pageable);
            participants.forEach(participant -> {
                var newDuration = ChallengeParticipantDurationRange.merge(participant.getDurationRange(), duration);
                participant.changeStatus(ChallengeParticipantStatus.UNCOMPLETED, newDuration);
            });
            pageable = participants.nextPageable();
        } while (participants.hasNext());
    }

}
