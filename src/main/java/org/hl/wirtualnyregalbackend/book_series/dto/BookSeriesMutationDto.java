package org.hl.wirtualnyregalbackend.book_series.dto;

import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record BookSeriesMutationDto(
    @StringConstraints
    String name
) {
}
