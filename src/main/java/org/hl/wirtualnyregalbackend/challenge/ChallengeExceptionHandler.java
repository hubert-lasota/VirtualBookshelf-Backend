package org.hl.wirtualnyregalbackend.challenge;

import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.challenge.exception.InvalidChallengeDurationRangeException;
import org.hl.wirtualnyregalbackend.common.error.FieldValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ChallengeExceptionHandler {

    @ExceptionHandler(InvalidChallengeDurationRangeException.class)
    public ProblemDetail handleInvalidChallengeDurationRangeException(InvalidChallengeDurationRangeException exc) {
        FieldValidationError error = exc.getError();
        log.debug("Handling InvalidChallengeDurationRangeException: {}", error);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        problemDetail.setProperty("errors", List.of(error));
        return problemDetail;
    }

}
