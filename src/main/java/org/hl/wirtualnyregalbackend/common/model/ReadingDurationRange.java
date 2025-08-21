package org.hl.wirtualnyregalbackend.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.error.exception.InvalidReadingDurationRangeException;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;

@Embeddable
public record ReadingDurationRange(
    @NotNull(groups = CreateGroup.class)
    @Column(name = "started_reading_at")
    Instant startedAt,
    @NotNull(groups = CreateGroup.class)
    @Column(name = "finished_reading_at") // TODO not working hibernate mapuje na odwrot pola
    Instant finishedAt
) {

    public static ReadingDurationRange merge(ReadingDurationRange oldRdr, @Nullable ReadingDurationRange newRdr) {
        if (newRdr == null) {
            return oldRdr;
        }

        Instant startedAt = newRdr.startedAt() != null ? newRdr.startedAt() : oldRdr.startedAt();
        Instant finishedAt = newRdr.finishedAt() != null ? newRdr.finishedAt() : oldRdr.finishedAt();
        return ReadingDurationRange.of(startedAt, finishedAt);
    }

    public static ReadingDurationRange of(Instant startedAt, @Nullable Instant finishedAt) throws InvalidReadingDurationRangeException {
        ReadingDurationRange rdr = new ReadingDurationRange(startedAt, finishedAt);
        if (!isValid(startedAt, finishedAt)) {
            throw new InvalidReadingDurationRangeException(rdr);
        }
        return rdr;
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
