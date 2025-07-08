package org.hl.wirtualnyregalbackend.challenge.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record ChallengeResponseDto(
    Long id,
    @JsonUnwrapped
    ChallengeMutationDto challengeDto,
    Long totalParticipants
) {
}
