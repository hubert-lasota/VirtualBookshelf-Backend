package org.hl.wirtualnyregalbackend.reading_session.dto;


import org.hl.wirtualnyregalbackend.common.reading.PageRange;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;

import java.util.List;

public record ReadingSessionResponse(
    Long id,
    String title,
    PageRange pageRange,
    SessionReadingDurationRange durationRange,
    List<NoteInSessionDto> notes
) {
}
