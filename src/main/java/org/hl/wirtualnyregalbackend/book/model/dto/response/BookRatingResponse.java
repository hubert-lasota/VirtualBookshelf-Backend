package org.hl.wirtualnyregalbackend.book.model.dto.response;

import org.hl.wirtualnyregalbackend.user.model.dto.UserHeaderResponse;

import java.time.Instant;

public record BookRatingResponse(Long id,
                                 UserHeaderResponse user,
                                 Float rating,
                                 String justification,
                                 Instant createdAtTimestamp,
                                 Instant updatedAtTimestamp) {
}
