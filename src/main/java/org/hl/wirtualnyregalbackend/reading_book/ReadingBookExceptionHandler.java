package org.hl.wirtualnyregalbackend.reading_book;

import org.hl.wirtualnyregalbackend.reading_book.exception.InvalidReadingBookDurationRangeException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
class ReadingBookExceptionHandler {

    @ExceptionHandler(InvalidReadingBookDurationRangeException.class)
    public ProblemDetail handleInvalidReadingBookDurationRangeException(InvalidReadingBookDurationRangeException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(org.springframework.http.HttpStatus.BAD_REQUEST, "Validation Failed");
        pd.setProperty("errors", List.of(ex.getError()));
        return pd;
    }

}
