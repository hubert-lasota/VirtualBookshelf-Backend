package org.hl.wirtualnyregalbackend.reading_session.dto;


import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.common.model.ReadingDurationRange;

public record ReadingSessionResponse(
    Long id,
    PageRange pageRange,
    ReadingDurationRange durationRange,
    BookResponse book
) {
}
