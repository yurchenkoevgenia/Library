package org.example.ind.library.exception;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.example.ind.library.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException exception, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleBadRequest(Exception exception, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(apiError);
    }
}

