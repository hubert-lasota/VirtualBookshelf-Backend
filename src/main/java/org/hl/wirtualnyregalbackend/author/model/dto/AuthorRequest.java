package org.hl.wirtualnyregalbackend.author.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(@NotBlank String fullName) {
}
