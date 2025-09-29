package org.hl.wirtualnyregalbackend.challenge.model;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.common.model.InstantRangeFilter;
import org.springframework.lang.Nullable;

public record ChallengeFilter(
    @Nullable
    Boolean participating,
    @Nullable
    String query,
    @Nullable
    @Valid
    InstantRangeFilter durationRange,
    @Nullable
    ChallengeType type
) {
}
