package com.dorayd.sports.core.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice("com.dorayd.sports")
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAllExceptions(Exception exception) {
        return ErrorResponse
                .builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ProblemDetail validationProblemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation error");

        List<ConstraintViolation> errors = exception.getFieldErrors()
                .stream()
                .map(violation -> ConstraintViolation.builder()
                        .message(violation.getDefaultMessage())
                        .fieldName(violation.getField())
                        .rejectedValue(Objects.isNull(violation.getRejectedValue())
                            ? "null"
                            : violation.getRejectedValue().toString())
                        .build())
                .toList();

        validationProblemDetail.setProperty("errors", errors);
        return validationProblemDetail;
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ProblemDetail handleIllegalAccessException(IllegalAccessException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
