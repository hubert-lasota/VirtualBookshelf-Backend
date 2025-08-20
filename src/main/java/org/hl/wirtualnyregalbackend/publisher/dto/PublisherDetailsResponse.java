package org.hl.wirtualnyregalbackend.publisher.dto;

import java.time.Instant;

public record PublisherDetailsResponse(
    Long id,
    String name,
    Instant createdAt,
    Instant updatedAt
) {
}
