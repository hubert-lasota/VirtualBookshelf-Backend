package org.hl.wirtualnyregalbackend.reading_book.dto;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingStatus;

public record ChangeStatusRequestDto(ReadingStatus status) {
}
