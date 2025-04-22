package org.hl.wirtualnyregalbackend.challenge.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.jpa.RangeDate;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "challenge_participant_details")
public class ChallengeParticipantDetails extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "startAt", column = @Column(name = "started_at")),
        @AttributeOverride(name = "finishAt", column = @Column(name = "finished_at"))
    })
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected ChallengeParticipantDetails() {
    }

    public ChallengeParticipantDetails(Challenge challenge, Instant startedAt, User user) {
        this.challenge = Objects.requireNonNull(challenge, "challenge cannot be null");
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.status = ChallengeStatus.STARTED;
        this.rangeDate = new RangeDate(startedAt, null);
    }

    public void changeStatusToWon(Instant finishedAt) {
        changeStatusToOtherThanStarted(ChallengeStatus.WON, finishedAt);
    }

    public void changeStatusToLost(Instant finishedAt) {
        changeStatusToOtherThanStarted(ChallengeStatus.LOST, finishedAt);
    }

    private void changeStatusToOtherThanStarted(ChallengeStatus newStatus, Instant finishedAt) {
        if (status == ChallengeStatus.STARTED) {
            this.status = newStatus;
            Instant startedAt = rangeDate.getStartAt();
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
