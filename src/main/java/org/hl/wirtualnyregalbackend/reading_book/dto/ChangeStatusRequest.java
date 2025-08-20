package org.hl.wirtualnyregalbackend.reading_book.dto;

import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;

public record ChangeStatusRequest(ReadingStatus status) {
}
