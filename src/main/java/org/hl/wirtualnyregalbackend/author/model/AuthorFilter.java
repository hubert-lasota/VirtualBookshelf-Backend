package org.hl.wirtualnyregalbackend.author.model;

import org.springframework.lang.Nullable;

public record AuthorFilter(
    @Nullable
    Boolean availableInBookshelf
) {
}
