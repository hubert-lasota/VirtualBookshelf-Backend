package org.hl.wirtualnyregalbackend.infrastructure.book.dto.response;

import org.hl.wirtualnyregalbackend.application.book.BookReadingStatus;

import java.time.Instant;

public record BookReadingDetailsResponse(Integer currentPage,
                                         Integer progressPercentage,
                                         BookReadingStatus status,
                                         Instant startedAtTimestamp,
                                         Instant finishedAtTimestamp) {
}
