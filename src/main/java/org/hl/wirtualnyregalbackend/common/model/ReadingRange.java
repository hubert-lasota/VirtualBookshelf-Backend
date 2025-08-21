package org.hl.wirtualnyregalbackend.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.error.exception.InvalidReadingRangeException;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;

@Embeddable
public record ReadingRange(
    @NotNull(groups = CreateGroup.class)
    @Column(name = "started_reading_at")
    Instant startedAt,
    @NotNull(groups = CreateGroup.class)
    @Column(name = "finished_reading_at")
    Instant finishedAt
) {

    public static ReadingRange merge(ReadingRange oldRr, @Nullable ReadingRange newRr) {
        if (newRr == null) {
            return oldRr;
        }

        Instant startedAt = newRr.startedAt() != null ? newRr.startedAt() : oldRr.startedAt();
        Instant finishedAt = newRr.finishedAt() != null ? newRr.finishedAt() : oldRr.finishedAt();
        return ReadingRange.of(startedAt, finishedAt);
    }

    public static ReadingRange of(Instant startedAt, @Nullable Instant finishedAt) throws InvalidReadingRangeException {
        ReadingRange rr = new ReadingRange(startedAt, finishedAt);
        if (!isValid(startedAt, finishedAt)) {
            throw new InvalidReadingRangeException(rr);
        }
        return rr;
    }

    private static boolean isValid(Instant startedAt, @Nullable Instant finishedAt) {
        if (finishedAt == null) {
            return true;
        }

        return startedAt.isBefore(finishedAt);
    }

    @JsonIgnore
    public Integer getReadMinutes() {
        if (finishedAt == null) {
            throw new IllegalStateException("finishedAt is null. Cannot calculate minutes");
        }
        return (int) Duration.between(startedAt, finishedAt).toMinutes();
    }

    @AssertTrue(groups = CreateGroup.class, message = "finishedAt must be greater than startedAt")
    @JsonIgnore
    public boolean validate() {
        return isValid(startedAt, finishedAt);
    }

}
