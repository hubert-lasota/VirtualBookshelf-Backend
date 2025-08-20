package org.hl.wirtualnyregalbackend.challenge_participant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import org.hl.wirtualnyregalbackend.common.exception.InvalidFieldsException;
import org.hl.wirtualnyregalbackend.common.model.ApiFieldError;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.List;

@Embeddable
public record ChallengeParticipantDurationRange(
    @Column(name = "started_at")
    Instant startedAt,
    @Column(name = "finished_at")
    @Nullable
    Instant finishedAt
) {


    public static ChallengeParticipantDurationRange merge(ChallengeParticipantDurationRange oldDr, @Nullable ChallengeParticipantDurationRange newDr) {
        if (newDr == null) {
            return oldDr;
        }
        Instant startedAt = newDr.startedAt() != null ? newDr.startedAt() : oldDr.startedAt();
        Instant finishedAt = newDr.finishedAt() != null ? newDr.finishedAt() : oldDr.finishedAt();
        if (!isValid(startedAt, finishedAt)) {
            ApiFieldError err = new ApiFieldError("durationRange", "finishedAt must be greater or equal startedAt", newDr);
            throw new InvalidFieldsException(List.of(err));
        }
        return new ChallengeParticipantDurationRange(startedAt, finishedAt);
    }

    private static boolean isValid(Instant startedAt, @Nullable Instant finishedAt) {
        if (finishedAt == null) {
            return true;
        }

        return startedAt.isBefore(finishedAt);
    }

    @AssertTrue(groups = CreateGroup.class, message = "finishedAt must be greater or equal startedAt")
    public boolean validate() {
        return isValid(startedAt, finishedAt);
    }

}
