package ru.practicum.explore.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.explore.exception.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice

public class Handler extends ResponseEntityExceptionHandler {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(NotFoundEx.class)
    public ResponseEntity<Object> handleNotFound(NotFoundEx exception) {
        return new ResponseEntity<>(new ErrorResponse
                (new String[]{}, exception.getMessage(), exception.getMessage(), "NOT_FOUND"),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentEx.class)
    public ResponseEntity<Object> handleBadRequest(IllegalArgumentEx exception) {
        return new ResponseEntity<>(new ErrorResponse
                (new String[]{}, exception.getMessage(), exception.getMessage(), "FORBIDDEN"),
                HttpStatus.BAD_REQUEST);
    }

    private class ErrorResponse {
        @JsonProperty("errors")
        private String[] errors;
        @JsonProperty("message")
        private String message;
        @JsonProperty("reason")
        private String reason;
        @JsonProperty("status")
        private String status;
        @JsonProperty("timestamp")
        private String timestamp;

        public ErrorResponse(String[] errors, String message, String reason, String status) {
            this.errors = errors;
            this.message = message;
            this.reason = reason;
            this.status = status;
            this.timestamp = LocalDateTime.now().format(FORMATTER);
        }
    }
}
