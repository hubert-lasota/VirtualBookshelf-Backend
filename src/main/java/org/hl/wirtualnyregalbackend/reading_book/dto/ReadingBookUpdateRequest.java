package org.hl.wirtualnyregalbackend.reading_book.dto;

import jakarta.validation.Valid;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;


public record ReadingBookUpdateRequest(ReadingStatus status, @Valid ReadingBookDurationRange durationRange) {
}
