package org.hl.wirtualnyregalbackend.author.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.Instant;

public record AuthorResponseDto(
    Long id,
    @JsonUnwrapped
    AuthorMutationDto authorDto,
    Instant createdAt,
    Instant updatedAt
) {
}
