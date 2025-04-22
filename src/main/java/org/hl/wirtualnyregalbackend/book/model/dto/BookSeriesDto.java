package org.hl.wirtualnyregalbackend.book.model.dto;

import jakarta.validation.constraints.Min;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record BookSeriesDto(
    Long id,

    @StringConstraints
    String name,

    @Min(0)
    Integer bookOrder
) {
}
