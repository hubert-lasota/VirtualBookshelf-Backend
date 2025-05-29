package org.hl.wirtualnyregalbackend.challenge.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "challenge_participant")
public class ChallengeParticipant extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Embedded
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected ChallengeParticipant() {
    }

    public ChallengeParticipant(Challenge challenge, RangeDate rangeDate, User user) {
        this.challenge = Objects.requireNonNull(challenge, "challenge cannot be null");
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.status = ChallengeStatus.STARTED;
        this.rangeDate = rangeDate;
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
            Instant startedAt = rangeDate.getStartedAt();
            this.rangeDate = new RangeDate(startedAt, finishedAt);
        } else {
            throw new InvalidRequestException("You can't change status to %s if status is not STARTED".formatted(newStatus.toString()));
        }
    }

    public ChallengeStatus getStatus() {
        return status;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public User getUser() {
        return user;
    }

    public RangeDate getRangeDate() {
        return rangeDate;
    }

}
