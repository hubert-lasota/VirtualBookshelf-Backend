package org.hl.wirtualnyregalbackend.reading_book.model;

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
import org.hl.wirtualnyregalbackend.reading_book.exception.InvalidReadingBookDurationRangeException;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.Instant;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReadingBookDurationRange {

    @NotNull(groups = CreateGroup.class)
    @Column(name = "started_reading_at")
    private Instant startedAt;
    @Nullable
    @Column(name = "finished_reading_at")
    private Instant finishedAt;


    public static ReadingBookDurationRange merge(ReadingBookDurationRange oldRbdr, @Nullable ReadingBookDurationRange newRbdr) {
        if (newRbdr == null) {
            return oldRbdr;
        }

        Instant startedAt = newRbdr.startedAt != null ? newRbdr.startedAt : oldRbdr.startedAt;
        Instant finishedAt = newRbdr.finishedAt != null ? newRbdr.finishedAt : oldRbdr.finishedAt;
        return ReadingBookDurationRange.of(startedAt, finishedAt);
    }

    public static ReadingBookDurationRange of(Instant startedAt, @Nullable Instant finishedAt) throws InvalidReadingBookDurationRangeException {
        ReadingBookDurationRange rbdr = new ReadingBookDurationRange(startedAt, finishedAt);
        if (!isValid(startedAt, finishedAt)) {
            throw new InvalidReadingBookDurationRangeException(rbdr);
        }
        return rbdr;
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
