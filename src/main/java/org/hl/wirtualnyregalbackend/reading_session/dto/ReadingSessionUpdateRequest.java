package org.hl.wirtualnyregalbackend.reading_session.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.common.model.ReadingRange;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.lang.Nullable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NotAllFieldsNull(groups = UpdateGroup.class)
public class ReadingSessionUpdateRequest {

    @Valid
    @NotNull(groups = CreateGroup.class)
    protected PageRange pageRange;

    @Valid
    @NotNull(groups = CreateGroup.class)
    protected ReadingRange readingRange;

    @Nullable
    protected String description;

}
