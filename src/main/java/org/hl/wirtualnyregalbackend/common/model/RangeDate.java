package org.hl.wirtualnyregalbackend.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

import java.time.Instant;

@Embeddable
@ValidRangeDate
public class RangeDate {

    @NotNull(groups = CreateGroup.class)
    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    protected RangeDate() {
    }

    public RangeDate(Instant startedAt, Instant endedAt) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

}
