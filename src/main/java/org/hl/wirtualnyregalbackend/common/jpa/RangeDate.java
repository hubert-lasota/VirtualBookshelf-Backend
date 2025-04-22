package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.Embeddable;

import java.time.Instant;
import java.util.Objects;

@Embeddable
public class RangeDate {

    private Instant startAt;
    private Instant endAt;

    protected RangeDate() {
    }

    public RangeDate(Instant startAt, Instant endAt) {
        this.startAt = Objects.requireNonNull(startAt, "startAt cannot be null");
        if (endAt != null && (endAt.isBefore(startAt) || endAt.equals(startAt))) {
            throw new IllegalArgumentException("finishAt must be after startAt");
        }
        this.endAt = endAt;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

}
