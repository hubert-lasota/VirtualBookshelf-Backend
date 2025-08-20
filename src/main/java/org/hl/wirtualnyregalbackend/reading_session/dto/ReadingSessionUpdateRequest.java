package org.hl.wirtualnyregalbackend.reading_session.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.reading_session.model.ReadingRange;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NotAllFieldsNull
public class ReadingSessionUpdateRequest {

    @Valid
    protected PageRange pageRange;

    @Valid
    protected ReadingRange readingRange;

    protected String description;

}
