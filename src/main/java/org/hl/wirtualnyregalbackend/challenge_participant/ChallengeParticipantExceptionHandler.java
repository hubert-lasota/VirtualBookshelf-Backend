package org.hl.wirtualnyregalbackend.challenge_participant;

import org.hl.wirtualnyregalbackend.challenge_participant.exception.InvalidChallengeParticipantDurationRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
class ChallengeParticipantExceptionHandler {

    @ExceptionHandler(InvalidChallengeParticipantDurationRangeException.class)
    public ProblemDetail handleInvalidChallengeParticipantDurationRangeException(InvalidChallengeParticipantDurationRangeException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        pd.setProperty("errors", List.of(ex.getError()));
        return pd;
    }

}
