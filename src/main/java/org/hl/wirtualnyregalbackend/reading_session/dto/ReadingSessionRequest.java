package org.hl.wirtualnyregalbackend.reading_session.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.reading.PageRange;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;

import java.util.List;

public record ReadingSessionRequest(
    @NotNull(groups = CreateGroup.class)
    Long readingBookId, // this field is not used in update
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(50)
    String title,
    @Valid
    @NotNull(groups = CreateGroup.class)
    PageRange pageRange,
    @Valid
    @NotNull(groups = CreateGroup.class)
    SessionReadingDurationRange durationRange,
    @Valid
    List<NoteInSessionDto> notes
) {
}
