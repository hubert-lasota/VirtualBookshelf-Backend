package org.hl.wirtualnyregalbackend.author.dto;

import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;

import java.time.Instant;

public record AuthorDetailsResponse(
    Long id,
    String fullName,
    String profilePictureUrl,
    String description,
    Integer totalReviews,
    Double averageRating,
    ReviewResponse review,
    Instant createdAt,
    Instant updatedAt
) {
}
