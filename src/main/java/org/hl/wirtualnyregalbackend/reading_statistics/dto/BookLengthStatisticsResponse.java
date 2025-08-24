package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import org.hl.wirtualnyregalbackend.reading_statistics.entity.BookLength;

public record BookLengthStatisticsResponse(
    BookLength length,
    Long bookCount,
    Long readBookCount
) {
}
