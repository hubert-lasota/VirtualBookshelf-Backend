package org.hl.wirtualnyregalbackend.application.challenge;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.common.ActionResult;
import org.hl.wirtualnyregalbackend.application.common.ApiError;
import org.hl.wirtualnyregalbackend.application.common.RangeDate;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "challenge_participant_details")
public class ChallengeParticipantDetails extends UpdatableBaseEntity {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startAt", column = @Column(name = "started_at_timestamp")),
            @AttributeOverride(name = "finishAt", column = @Column(name = "finished_at_timestamp"))
    })
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected ChallengeParticipantDetails() { }

    public ChallengeParticipantDetails(Challenge challenge, RangeDate rangeDate, User user) {
        this.challenge = Objects.requireNonNull(challenge, "challenge cannot be null");
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.status = ChallengeStatus.STARTED;
    }

    public ActionResult changeStatusToWon(Instant finishedAt) {
        return changeStatusToOtherThanStarted(ChallengeStatus.WON, finishedAt);
    }

    public ActionResult changeStatusToLost(Instant finishedAt) {
        return changeStatusToOtherThanStarted(ChallengeStatus.LOST, finishedAt);
    }

    private ActionResult changeStatusToOtherThanStarted(ChallengeStatus newStatus, Instant finishedAt) {
        if(status == ChallengeStatus.STARTED) {
            this.status = newStatus;
            Instant startedAt = rangeDate.getStartAt();
            this.rangeDate = new RangeDate(startedAt, finishedAt);
            return new  ActionResult(true, null);
        } else {
            ApiError error = new ApiError("status","You can't change status to %s if status is not STARTED".formatted(newStatus.toString()));
            return new ActionResult(false, error);

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
