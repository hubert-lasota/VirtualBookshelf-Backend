package org.hl.wirtualnyregalbackend.challenge_participant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "challenge_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeParticipant extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

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
        this.status = ChallengeStatus.STARTED;
        this.startedAt = startedAt;
    }

    public void won(Instant finishedAt) {
        changeStatusToOtherThanStarted(ChallengeStatus.WON, finishedAt);
    }

    public void lost(Instant finishedAt) {
        changeStatusToOtherThanStarted(ChallengeStatus.LOST, finishedAt);
    }


    private void changeStatusToOtherThanStarted(ChallengeStatus newStatus, Instant finishedAt) {
        if (status == ChallengeStatus.STARTED) {
            this.status = newStatus;
            this.finishedAt = finishedAt;
        } else {
            throw new InvalidRequestException("You can't change status to %s if status is not STARTED".formatted(newStatus.toString()));
        }
    }

}
