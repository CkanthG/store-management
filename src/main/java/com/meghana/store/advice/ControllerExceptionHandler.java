package com.meghana.store.advice;

import com.meghana.store.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This controller advice is sending proper exception message to UI.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * This method is sent proper error message to end user.
     *
     * @param e exception object
     * @return exception message
     */
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ProblemDetail entityNotFoundException(Exception e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
