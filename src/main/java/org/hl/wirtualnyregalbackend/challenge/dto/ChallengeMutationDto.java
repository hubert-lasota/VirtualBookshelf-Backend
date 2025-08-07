package org.hl.wirtualnyregalbackend.challenge.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hl.wirtualnyregalbackend.challenge.entity.ChallengeType;
import org.hl.wirtualnyregalbackend.common.validation.RangeDateValidator;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

import java.time.Instant;

public record ChallengeMutationDto(
    @StringConstraints
    @Max(75)
    String title,
    @Max(1000)
    String description,
    ChallengeType type,
    Instant startAt,
    Instant endAt,
    @Min(1)
    Integer targetCount,
    Long genreId
) {

    @AssertTrue(message = "startAt must be before endAt")
    public boolean isValid() {
        return RangeDateValidator.isValid(startAt(), endAt());
    }

}
