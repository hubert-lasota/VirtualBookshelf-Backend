package org.hl.wirtualnyregalbackend.book.model.dto.response;

import org.hl.wirtualnyregalbackend.book.model.BookReadingStatus;

import java.time.Instant;

public record BookReadingDetailsResponse(Integer currentPage,
                                         Integer progressPercentage,
                                         BookReadingStatus status,
                                         Instant startedAt,
                                         Instant finishedAt) {
}
