package org.hl.wirtualnyregalbackend.common.model;

import jakarta.validation.constraints.AssertTrue;
import org.springframework.lang.Nullable;

public record DoubleRangeFilter(
    @Nullable Double lte,
    @Nullable Double gte
) {

    @AssertTrue(message = "lte must be lower or equals to gte")
    public boolean isValid() {
        if (lte == null || gte == null) {
            return true;
        }

        return lte >= gte;
    }
}
