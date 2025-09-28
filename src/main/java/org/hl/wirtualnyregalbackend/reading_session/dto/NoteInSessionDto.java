package org.hl.wirtualnyregalbackend.reading_session.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record NoteInSessionDto(
    Long id,
    @NotNull
    @StringConstraints
    @Max(50)
    String title,
    @StringConstraints(allowNotTrimmed = true)
    String content
) {
}
