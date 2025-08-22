package org.hl.wirtualnyregalbackend.reading_session.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.reading_session.exception.InvalidSessionReadingDurationRangeException;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionReadingDurationRange {

    @NotNull(groups = CreateGroup.class)
    @Column(name = "started_reading_at")
    private Instant startedAt;
    @NotNull(groups = CreateGroup.class)
    @Column(name = "finished_reading_at")
    private Instant finishedAt;

    public static SessionReadingDurationRange merge(SessionReadingDurationRange oldRdr, @Nullable SessionReadingDurationRange newRdr) {
        if (newRdr == null) {
            return oldRdr;
        }

        Instant startedAt = newRdr.startedAt != null ? newRdr.startedAt : oldRdr.startedAt;
        Instant finishedAt = newRdr.finishedAt != null ? newRdr.finishedAt : oldRdr.finishedAt;
        return SessionReadingDurationRange.of(startedAt, finishedAt);
    }

    public static SessionReadingDurationRange of(Instant startedAt, Instant finishedAt) throws InvalidSessionReadingDurationRangeException {
        SessionReadingDurationRange rdr = new SessionReadingDurationRange(startedAt, finishedAt);
        if (!isValid(startedAt, finishedAt)) {
            throw new InvalidSessionReadingDurationRangeException(rdr);
        }
        return rdr;
    }

    private static boolean isValid(Instant startedAt, Instant finishedAt) {
        return startedAt.isBefore(finishedAt);
    }

    public Integer getReadMinutes() {
        return (int) Duration.between(startedAt, finishedAt).toMinutes();
    }

    @AssertTrue(groups = CreateGroup.class, message = "finishedAt must be greater than startedAt")
    @JsonIgnore
    public boolean validate() {
        return isValid(startedAt, finishedAt);
    }

}
