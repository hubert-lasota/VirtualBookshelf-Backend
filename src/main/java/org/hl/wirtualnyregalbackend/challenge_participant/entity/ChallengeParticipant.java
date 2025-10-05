package org.hl.wirtualnyregalbackend.challenge_participant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
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
    private int currentGoalValue;

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
        if (currentGoalValue == 0) {
            return 0F;
        }
        float value = ((float) currentGoalValue) / challenge.getGoalValue() * 100F;
        return BigDecimal
            .valueOf(value)
            .setScale(2, RoundingMode.HALF_UP)
            .floatValue();
    }

    public void changeStatus(ChallengeParticipantStatus status, ChallengeParticipantDurationRange durationRange) {
        this.status = status;
        if (status == ChallengeParticipantStatus.ACTIVE) {
            this.durationRange = ChallengeParticipantDurationRange.of(durationRange.startedAt(), null);
        } else {
            this.durationRange = durationRange;
        }
    }

    public void addCurrentCount(int count) {
        Integer goalValue = challenge.getGoalValue();
        if (goalValue.equals(currentGoalValue)) {
            return;
        }

        int newValue = currentGoalValue + count;
        if (newValue > goalValue) {
            this.currentGoalValue = goalValue;
        } else if (newValue < 0) {
            this.currentGoalValue = 0;
        } else {
            this.currentGoalValue += count;
        }
    }

    public void incrementCurrentCount() {
        addCurrentCount(1);
    }

}
