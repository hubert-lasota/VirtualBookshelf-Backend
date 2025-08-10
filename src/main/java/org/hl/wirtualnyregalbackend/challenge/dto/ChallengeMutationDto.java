package org.hl.wirtualnyregalbackend.challenge.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.RangeDateValidator;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

import java.time.Instant;

public record ChallengeMutationDto(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(75)
    String title,
    @NotNull(groups = CreateGroup.class)
    @Max(1000)
    String description,
    @NotNull(groups = CreateGroup.class)
    ChallengeType type,
    @NotNull(groups = CreateGroup.class)
    Instant startAt,
    @NotNull(groups = CreateGroup.class)
    Instant endAt,
    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer targetCount,
    Long genreId
) {

    @AssertTrue(message = "startAt must be before endAt")
    public boolean isValid() {
        return RangeDateValidator.isValid(startAt(), endAt());
    }

}
