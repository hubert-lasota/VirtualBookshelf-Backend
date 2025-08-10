package org.hl.wirtualnyregalbackend.author.dto;

import java.time.Instant;

public record AuthorDetailsResponseDto(
    Long id,
    String fullName,
    String photoUrl,
    String description,
    Instant createdAt,
    Instant updatedAt
) {
}
