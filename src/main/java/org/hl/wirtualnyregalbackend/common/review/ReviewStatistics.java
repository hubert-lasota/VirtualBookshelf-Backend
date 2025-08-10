package org.hl.wirtualnyregalbackend.common.review;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ReviewStatistics(
    @JsonIgnore
    Long entityId,
    Double average,
    Long total) {
}
