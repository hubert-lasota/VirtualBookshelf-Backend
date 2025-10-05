package org.hl.wirtualnyregalbackend.reading_statistics.dto;

import org.hl.wirtualnyregalbackend.reading_statistics.model.BookLength;

public record BookLengthStatisticsResponse(
    BookLength length,
    long bookCount,
    long readBookCount
) {
}
