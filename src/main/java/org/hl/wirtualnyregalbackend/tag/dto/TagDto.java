package org.hl.wirtualnyregalbackend.tag.dto;

import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record TagDto(
    Long id,

    @StringConstraints
    String name
) {
}
