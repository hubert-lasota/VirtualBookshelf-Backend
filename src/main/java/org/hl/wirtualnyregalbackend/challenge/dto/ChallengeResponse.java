package org.hl.wirtualnyregalbackend.challenge.dto;

import org.hl.wirtualnyregalbackend.challenge.model.ChallengeDurationRange;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.genre.dto.GenreResponse;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;

public record ChallengeResponse(
    Long id,
    String title,
    String description,
    ChallengeType type,
    Integer goalValue,
    ChallengeDurationRange durationRange,
    GenreResponse genre,
    Long totalParticipants,
    ChallengeParticipation participation,
    UserResponse user
) {
}
