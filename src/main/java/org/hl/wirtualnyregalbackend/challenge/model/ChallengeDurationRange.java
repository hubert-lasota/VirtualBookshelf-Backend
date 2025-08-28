package org.hl.wirtualnyregalbackend.challenge.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hl.wirtualnyregalbackend.challenge.exception.InvalidChallengeDurationRangeException;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class ChallengeDurationRange {

    @NotNull
    private Instant startAt;
    @NotNull
    private Instant endAt;


    public static ChallengeDurationRange merge(ChallengeDurationRange oldCdr, @Nullable ChallengeDurationRange newCdr) {
        if (newCdr == null) {
            return oldCdr;
        }
        Instant startAt = newCdr.startAt != null ? newCdr.startAt : oldCdr.startAt;
        Instant endAt = newCdr.endAt != null ? newCdr.endAt : oldCdr.endAt;
        ChallengeDurationRange durationRange = new ChallengeDurationRange(startAt, endAt);
        if (!isValid(startAt, endAt)) {
            throw new InvalidChallengeDurationRangeException(durationRange);
        }
        return durationRange;
    }

    private static boolean isValid(Instant startAt, Instant endAt) {
        return startAt.isBefore(endAt);
    }

    @AssertTrue(message = "endAt must be before startAt")
    public boolean validate() {
        return isValid(startAt, endAt);
    }

}
