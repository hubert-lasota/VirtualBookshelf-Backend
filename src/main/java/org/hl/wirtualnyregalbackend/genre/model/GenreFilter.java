package org.hl.wirtualnyregalbackend.genre.model;

import org.springframework.lang.Nullable;

public record GenreFilter(
    @Nullable
    Boolean availableInBookshelf
) {
}
