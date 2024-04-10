package com.sensilabs.projecthub.commons;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public ApplicationErrorResponse handleApplicationException(ApplicationErrorResponse errorResponse) {
        Map<String, String> errors = new HashMap<>();

        return ApplicationErrorResponse.builder()
                .timestamp(Instant.now())
                .status(SERVER_ERROR.value())
                .errorCode(errorResponse.getErrorCode())
                .message(errorResponse.getMessage())
                .errors(errors)
                .build();
    }

    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApplicationErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getConstraintViolations().forEach(constraintViolation -> {
            String[] exceptionNodes = constraintViolation.getPropertyPath().toString().split("[.]");
            String shortExceptionPath = exceptionNodes.length > 1 ?
                    exceptionNodes[exceptionNodes.length - 1] :
                    exceptionNodes[0];
            errors.put(shortExceptionPath, constraintViolation.getMessage());
        });

        return ApplicationErrorResponse.builder()
                .timestamp(Instant.now())
                .status(ErrorCode.VALIDATION_EXCEPTION.status)
                .errorCode(ErrorCode.VALIDATION_EXCEPTION)
                .message(ErrorCode.VALIDATION_EXCEPTION.message)
                .errors(errors)
                .build();
    }
}
