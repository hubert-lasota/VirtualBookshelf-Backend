package org.hl.wirtualnyregalbackend.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hl.wirtualnyregalbackend.common.exception.InvalidFieldsException;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;

import java.time.Instant;
import java.util.List;

@Embeddable
@ValidRangeDate
@Getter
public class RangeDate {

    @NotNull(groups = CreateGroup.class)
    @JsonProperty
    @Column(name = "started_at")
    private Instant startedAt;

    @JsonProperty
    @Column(name = "ended_at")
    private Instant endedAt;

    protected RangeDate() {
    }

    private RangeDate(Instant startedAt, Instant endedAt) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }


    public static RangeDate of(Instant startedAt, Instant endedAt) throws InvalidFieldsException {
        if (startedAt != null && endedAt != null && startedAt.isAfter(endedAt)) {
            ApiFieldError error = new ApiFieldError("startedAt", "Started date must be before ended date", startedAt);
            throw new InvalidFieldsException(List.of(error));
        }
        return new RangeDate(startedAt, endedAt);
    }

    public static RangeDate merge(RangeDate newRangeDate, RangeDate oldRangeDate) throws InvalidFieldsException {
        Instant startedAt = newRangeDate.getStartedAt() != null ? newRangeDate.getStartedAt() : oldRangeDate.getStartedAt();
        Instant endedAt = newRangeDate.getEndedAt() != null ? newRangeDate.getEndedAt() : oldRangeDate.getEndedAt();
        return RangeDate.of(startedAt, endedAt);
    }

}
