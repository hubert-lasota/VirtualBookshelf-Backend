package org.hl.wirtualnyregalbackend.review.model.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.user.model.dto.UserHeaderResponseDto;

import java.time.Instant;

public record ReviewResponseDto(
    Long id,
    UserHeaderResponseDto user,
    @JsonUnwrapped
    ReviewDto review,
    Instant createdAt,
    Instant updatedAt
) {
}
