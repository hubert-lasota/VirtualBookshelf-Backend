package org.hl.wirtualnyregalbackend.author.dto;

import org.hl.wirtualnyregalbackend.common.review.ReviewResponseDto;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;

import java.time.Instant;

public record AuthorDetailsResponseDto(
    Long id,
    String fullName,
    String photoUrl,
    String description,
    ReviewStatistics reviewStatistics,
    ReviewResponseDto review,
    Instant createdAt,
    Instant updatedAt
) {
}
