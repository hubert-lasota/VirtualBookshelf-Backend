package org.hl.wirtualnyregalbackend.common.model;

import jakarta.validation.constraints.AssertTrue;
import org.springframework.lang.Nullable;

public record RangeFilter(
    @Nullable Integer lte,
    @Nullable Integer gte
) {

    @AssertTrue(message = "lte must be lower or equals to gte")
    public boolean isValid() {
        if (lte == null || gte == null) {
            return true;
        }

        return lte >= gte;
    }

}
