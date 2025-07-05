package org.hl.wirtualnyregalbackend.common.review;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.user.dto.UserHeaderResponseDto;

import java.time.Instant;

public record ReviewResponseDto(
    Long id,
    @JsonUnwrapped
    ReviewDto reviewDto,
    UserHeaderResponseDto user,
    Instant createdAt,
    Instant updatedAt
) {
}
