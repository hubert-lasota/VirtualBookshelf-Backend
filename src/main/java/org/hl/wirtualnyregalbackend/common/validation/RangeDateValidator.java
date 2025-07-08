package org.hl.wirtualnyregalbackend.common.validation;

import org.hl.wirtualnyregalbackend.common.exception.InvalidFieldsException;
import org.hl.wirtualnyregalbackend.common.model.ApiFieldError;

import java.time.Instant;
import java.util.List;

public class RangeDateValidator {

    private RangeDateValidator() {
    }


    public static boolean isValid(Instant startAt, Instant endAt) {
        return startAt == null || endAt == null || !startAt.isAfter(endAt);
    }

    public static void validate(
        Instant startAt,
        Instant endAt,
        String startFieldName,
        String endFieldName
    ) throws InvalidFieldsException {
        if (!isValid(startAt, endAt)) {
            ApiFieldError error = new ApiFieldError(
                startFieldName,
                "'%s' must be before '%s'".formatted(startFieldName, endFieldName),
                startAt
            );
            throw new InvalidFieldsException(List.of(error));
        }
    }

}