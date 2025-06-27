package org.hl.wirtualnyregalbackend.common.review;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ReviewStats(
    @JsonIgnore
    Long entityId,
    Double average,
    Long total) {
}
