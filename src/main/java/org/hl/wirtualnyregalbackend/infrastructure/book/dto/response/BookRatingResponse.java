package org.hl.wirtualnyregalbackend.infrastructure.book.dto.response;

import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserHeaderResponse;

import java.time.Instant;

public record BookRatingResponse(Long id,
                                 UserHeaderResponse user,
                                 Float rating,
                                 String justification,
                                 Instant createdAtTimestamp,
                                 Instant updatedAtTimestamp) {
}
