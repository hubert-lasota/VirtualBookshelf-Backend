package org.hl.wirtualnyregalbackend.reading_note.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NotAllFieldsNull
public class ReadingNoteMutationDto {

    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    @Max(50)
    protected String title;

    @NotNull(groups = CreateGroup.class)
    @StringConstraints(allowNotTrimmed = true)
    protected String content;

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    protected Integer pageFrom;

    @NotNull(groups = CreateGroup.class)
    @Min(1)
    protected Integer pageTo;

}
