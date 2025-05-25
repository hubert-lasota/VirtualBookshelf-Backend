package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;

public record ReviewDto(
    @Rating
    Float rating,
    @StringConstraints
    String content
) {
}
