package org.hl.wirtualnyregalbackend.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;

import java.time.Instant;
import java.util.Objects;

@Embeddable
public class RangeDate {

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    protected RangeDate() {
    }

    public RangeDate(Instant startedAt, Instant endedAt) {
        this.startedAt = Objects.requireNonNull(startedAt, "startedAt cannot be null");
        if (endedAt != null && (endedAt.isBefore(startedAt) || endedAt.equals(startedAt))) {
            throw new InvalidRequestException("endedAt must be after startedAt");
        }
        this.endedAt = endedAt;
    }


    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

}
