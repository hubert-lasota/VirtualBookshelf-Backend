package org.hl.wirtualnyregalbackend.reading_statistics.model;

import org.hl.wirtualnyregalbackend.genre.entity.Genre;

public record GenreStatsSum(
    long bookCount,
    long readBookCount,
    Genre genre
) {
}
