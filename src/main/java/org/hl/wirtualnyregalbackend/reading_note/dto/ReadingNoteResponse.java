package org.hl.wirtualnyregalbackend.reading_note.dto;


import org.hl.wirtualnyregalbackend.common.model.PageRange;

import java.time.Instant;

public record ReadingNoteResponse(
    Long id,
    String title,
    String content,
    PageRange pageRange,
    Instant createdAt,
    Instant updatedAt
) {
}
