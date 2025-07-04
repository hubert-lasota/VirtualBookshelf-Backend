package org.hl.wirtualnyregalbackend.publisher.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Instant;

public record PublisherResponseDto(
    Long id,
    @JsonUnwrapped
    PublisherMutationDto dto,
    Instant createdAt,
    Instant updatedAt
) {
}

