package org.hl.wirtualnyregalbackend.challenge_participant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.entity.ChallengeStatus;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
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

    @Embedded
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ChallengeParticipant(Challenge challenge, RangeDate rangeDate, User user) {
        this.challenge = Objects.requireNonNull(challenge, "challenge cannot be null");
        this.user = Objects.requireNonNull(user, "user cannot be null");
        this.status = ChallengeStatus.STARTED;
        this.rangeDate = rangeDate;
    }

    public void won(Instant endedAt) {
        changeStatusToOtherThanStarted(ChallengeStatus.WON, endedAt);
    }

    public void lost(Instant endedAt) {
        changeStatusToOtherThanStarted(ChallengeStatus.LOST, endedAt);
    }


    private void changeStatusToOtherThanStarted(ChallengeStatus newStatus, Instant endedAt) {
        if (status == ChallengeStatus.STARTED) {
            this.status = newStatus;
            Instant startedAt = rangeDate.getStartedAt();
            this.rangeDate = RangeDate.of(startedAt, endedAt);
        } else {
            throw new InvalidRequestException("You can't change status to %s if status is not STARTED".formatted(newStatus.toString()));
        }
    }

}
