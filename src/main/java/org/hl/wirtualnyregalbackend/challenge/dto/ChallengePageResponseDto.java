package org.hl.wirtualnyregalbackend.challenge.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;

public record ChallengePageResponseDto(
    @JsonUnwrapped
    PageResponseDto<ChallengeResponseDto> page,
    ChallengeStatistics challengeStatistics
) {
}
