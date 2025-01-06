package org.hl.wirtualnyregalbackend.infrastructure.author.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(@NotBlank String fullName) {
}
