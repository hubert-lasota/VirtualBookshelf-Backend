package org.hl.wirtualnyregalbackend.author.dto;

import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;

import java.time.Instant;

public record AuthorDetailsResponse(
    Long id,
    String fullName,
    String photoUrl,
    String description,
    ReviewStatistics reviewStatistics,
    ReviewResponse review,
    Instant createdAt,
    Instant updatedAt
) {
}
