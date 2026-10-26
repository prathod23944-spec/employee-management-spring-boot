package com.example.employee_management.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle HTTP errors, such as employee not found
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(
            ResponseStatusException ex) {

        ApiError apiError = new ApiError(
                ex.getStatusCode().value(),
                ex.getReason(),
                List.of()
        );

        return ResponseEntity
                .status(ex.getStatusCode())
                .header("X-Error-Handler", "GlobalExceptionHandler")
                .body(apiError);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField()
                        + ": " + error.getDefaultMessage())
                .distinct()
                .toList();

        ApiError apiError = new ApiError(
                400,
                "Validation failed",
                errors
        );

        return ResponseEntity
                .badRequest()
                .header("X-Error-Handler", "GlobalExceptionHandler")
                .body(apiError);
    }

    // Handle invalid JSON or unreadable request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJson(
            HttpMessageNotReadableException ex) {

        logger.warn("Invalid or unreadable request body");

        ApiError apiError = new ApiError(
                400,
                "Invalid JSON request body. Please check your JSON syntax and data types.",
                List.of()
        );

        return ResponseEntity
                .badRequest()
                .header("X-Error-Handler", "GlobalExceptionHandler")
                .body(apiError);
    }

    // Handle unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex) {

        logger.error("Unexpected error occurred", ex);

        ApiError apiError = new ApiError(
                500,
                "An unexpected error occurred",
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("X-Error-Handler", "GlobalExceptionHandler")
                .body(apiError);
    }
}
