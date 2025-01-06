package org.hl.wirtualnyregalbackend.infrastructure.bookshelf.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

public record BookshelfRequest(
        @NotBlank
        String name,
        @NotBlank
        String type,
        @Nullable
        String description
) {
}
