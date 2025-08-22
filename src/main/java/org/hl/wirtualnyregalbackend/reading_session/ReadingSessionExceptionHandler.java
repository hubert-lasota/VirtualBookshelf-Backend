package org.hl.wirtualnyregalbackend.reading_session;

import org.hl.wirtualnyregalbackend.reading_session.exception.InvalidSessionReadingDurationRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ReadingSessionExceptionHandler {

    @ExceptionHandler(InvalidSessionReadingDurationRangeException.class)
    public ProblemDetail handleInvalidSessionReadingDurationRangeException(InvalidSessionReadingDurationRangeException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        pd.setProperty("errors", ex.getError());
        return pd;
    }

}
