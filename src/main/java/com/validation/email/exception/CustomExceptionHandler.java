package com.validation.email.exception;

import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    public CustomExceptionHandler() {
    }

    @ResponseBody
    @ExceptionHandler({UnAuthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<String> handleUnAuthorizedException(final Exception exception) {
        log.error("UnAuthorizedException handler:", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler({DataNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<String> handleDataNotFoundException(final Exception exception) {
        log.error("Data not found :", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
