package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.user.dto.UserResponse;

import java.time.Instant;

public record ReviewResponse(
    Long id,
    Float rating,
    String content,
    UserResponse user,
    Instant createdAt,
    Instant updatedAt
) {
}
