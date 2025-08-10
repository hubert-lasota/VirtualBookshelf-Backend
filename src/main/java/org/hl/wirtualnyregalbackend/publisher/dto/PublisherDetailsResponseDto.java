package org.hl.wirtualnyregalbackend.publisher.dto;

import java.time.Instant;

public record PublisherDetailsResponseDto(
    Long id,
    String name,
    Instant createdAt,
    Instant updatedAt
) {
}
