package org.hl.wirtualnyregalbackend.challenge_participant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge_participant.exception.InvalidChallengeParticipantStatusStateException;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantDurationRange;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.time.Instant;

@Entity
@Table(name = "challenge_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChallengeParticipant extends BaseEntity {

    @Column
    private Integer currentGoalValue;

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeParticipantStatus status;

    @Embedded
    private ChallengeParticipantDurationRange durationRange;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void completed(Instant finishedAt) {
        changeStatusToOtherThanActive(ChallengeParticipantStatus.COMPLETED, finishedAt);
    }

    public void uncompleted(Instant finishedAt) {
        changeStatusToOtherThanActive(ChallengeParticipantStatus.UNCOMPLETED, finishedAt);
    }

    public void incrementCurrentCount() {
        Integer goalValue = challenge.getGoalValue();
        if (!goalValue.equals(currentGoalValue)) {
            this.currentGoalValue++;
        }
    }


    private void changeStatusToOtherThanActive(ChallengeParticipantStatus newStatus, Instant finishedAt) {
        if (status == ChallengeParticipantStatus.ACTIVE) {
            this.status = newStatus;
            this.durationRange = ChallengeParticipantDurationRange.merge(this.durationRange, new ChallengeParticipantDurationRange(durationRange.startedAt(), finishedAt));
        } else {
            throw new InvalidChallengeParticipantStatusStateException("You can't change status to %s if status is not ACTIVE".formatted(newStatus.toString()));
        }
    }

}
