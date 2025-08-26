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

import java.math.BigDecimal;
import java.math.RoundingMode;

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


    public Float calculateProgressPercentage() {
        if (currentGoalValue.equals(0)) {
            return 0F;
        }
        float value = ((float) currentGoalValue) / challenge.getGoalValue() * 100F;
        return BigDecimal
            .valueOf(value)
            .setScale(2, RoundingMode.HALF_UP)
            .floatValue();
    }

    public void changeStatus(ChallengeParticipantStatus status, ChallengeParticipantDurationRange durationRange) {
        if (this.status == ChallengeParticipantStatus.ACTIVE) {
            this.status = status;
            this.durationRange = durationRange;
        } else {
            throw new InvalidChallengeParticipantStatusStateException("You can't change status to %s if status is not ACTIVE".formatted(status.toString()));
        }
    }

    public void incrementCurrentCount() {
        Integer goalValue = challenge.getGoalValue();
        if (!goalValue.equals(currentGoalValue)) {
            this.currentGoalValue++;
        }
    }

}
