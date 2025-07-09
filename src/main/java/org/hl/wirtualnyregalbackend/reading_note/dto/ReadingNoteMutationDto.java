package org.hl.wirtualnyregalbackend.reading_note.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record ReadingNoteMutationDto(

    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(50)
    String title,

    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowNotTrimmed = true)
    String content,

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer pageFrom,

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    Integer pageTo
) {
}
