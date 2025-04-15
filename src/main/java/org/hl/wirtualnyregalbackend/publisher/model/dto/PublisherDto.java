package org.hl.wirtualnyregalbackend.publisher.model.dto;

import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

@NotAllFieldsNull
public record PublisherDto(
    Long id,

    @NotNull(groups = CreatePublisherGroup.class)
    @StringConstraints
    String name) {

}
