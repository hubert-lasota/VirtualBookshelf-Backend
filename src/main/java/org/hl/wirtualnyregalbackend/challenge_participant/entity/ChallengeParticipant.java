package org.hl.wirtualnyregalbackend.challenge_participant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "challenge_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeParticipant extends BaseEntity {

    @Column
    private Integer currentCount;

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeParticipantStatus status;

    @Column
    private Instant startedAt;

    @Column
    private Instant finishedAt;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ChallengeParticipant(Challenge challenge, Instant startedAt, User user) {
        this.challenge = Objects.requireNonNull(challenge, "challenge cannot be null");
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.status = ChallengeParticipantStatus.ACTIVE;
        this.startedAt = startedAt;
        this.currentCount = 0;
    }

    public void completed(Instant finishedAt) {
        changeStatusToOtherThanActive(ChallengeParticipantStatus.COMPLETED, finishedAt);
    }

    public void uncompleted(Instant finishedAt) {
        changeStatusToOtherThanActive(ChallengeParticipantStatus.UNCOMPLETED, finishedAt);
    }

    public void incrementCurrentCount() {
        Integer targetCount = challenge.getTargetCount();
        if (!targetCount.equals(currentCount)) {
            this.currentCount++;
        }
    }


    private void changeStatusToOtherThanActive(ChallengeParticipantStatus newStatus, Instant finishedAt) {
        if (status == ChallengeParticipantStatus.ACTIVE) {
            this.status = newStatus;
            this.finishedAt = finishedAt;
        } else {
            throw new InvalidRequestException("You can't change status to %s if status is not ACTIVE".formatted(newStatus.toString()));
        }
    }

}
