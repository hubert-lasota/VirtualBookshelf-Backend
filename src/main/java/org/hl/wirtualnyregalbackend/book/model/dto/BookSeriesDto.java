package org.hl.wirtualnyregalbackend.book.model.dto;

import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record BookSeriesDto(
    Long id,
    @StringConstraints
    String name
) {
}
