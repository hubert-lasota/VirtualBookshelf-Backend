package org.hl.wirtualnyregalbackend.review.model.dto;

import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.review.validation.Rating;

public record ReviewDto(
    @Rating
    Float rating,
    @StringConstraints
    String content
) {
}
