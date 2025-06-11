package org.hl.wirtualnyregalbackend.publisher.dto;

import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record PublisherMutationDto(
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    String name) {
}
