package org.hl.wirtualnyregalbackend.reading_session.dto;


import org.hl.wirtualnyregalbackend.book.dto.BookResponse;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;

public record ReadingSessionResponse(
    Long id,
    PageRange pageRange,
    SessionReadingDurationRange durationRange,
    BookResponse book
) {
}
