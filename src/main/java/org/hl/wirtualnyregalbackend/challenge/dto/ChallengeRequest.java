package org.hl.wirtualnyregalbackend.challenge.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeDurationRange;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record ChallengeRequest(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(75)
    @Min(1)
    String title,
    @NotNull(groups = CreateGroup.class)
    @Max(500)
    @Min(1)
    String description,
    @NotNull(groups = CreateGroup.class)
    ChallengeType type,
    @NotNull(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    ChallengeDurationRange durationRange,
    @Min(1)
    Integer goalValue,
    Long genreId
) {
}
