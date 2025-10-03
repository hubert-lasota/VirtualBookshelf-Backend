package org.hl.wirtualnyregalbackend.common.model;

import jakarta.validation.constraints.AssertTrue;
import org.springframework.lang.Nullable;

import java.time.Instant;

public record InstantRange(
    @Nullable Instant lte,
    @Nullable Instant gte
) {

    @AssertTrue(message = "lte must be before or equals to gte")
    public boolean isValid() {
        if (lte == null || gte == null) {
            return true;
        }

        return lte.equals(gte) || lte.isBefore(gte);
    }

}
