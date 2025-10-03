package org.hl.wirtualnyregalbackend.challenge.model;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.common.model.InstantRange;
import org.springframework.lang.Nullable;

public record ChallengeFilter(
    @Nullable
    Boolean participating,
    @Nullable
    String query,
    @Nullable
    @Valid
    InstantRange durationRange,
    @Nullable
    ChallengeType type
) {
}
