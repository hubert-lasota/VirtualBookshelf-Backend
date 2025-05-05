package org.hl.wirtualnyregalbackend.book_reading.model.dto;

import org.hl.wirtualnyregalbackend.book_reading.model.BookReadingStatus;

import java.time.Instant;

public record BookReadingDetailsResponse(Integer currentPage,
                                         Integer progressPercentage,
                                         BookReadingStatus status,
                                         Instant startedAt,
                                         Instant finishedAt) {
}
