package org.hl.wirtualnyregalbackend.reading_note.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NotAllFieldsNull
public class ReadingNoteUpdateRequest {

    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(50)
    protected String title;

    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowNotTrimmed = true)
    protected String content;

    @Valid
    PageRange pageRange;

}
