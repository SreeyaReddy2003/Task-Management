package com.sprint_project.task_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(
            Map.of(
                "timestamp", LocalDateTime.now(),
                "code", "RUNTIME_ERROR",
                "message", ex.getMessage(),
                "path", request.getDescription(false)
            )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            Map.of(
                "timestamp", LocalDateTime.now(),
                "code", "INTERNAL_ERROR",
                "message", ex.getMessage(),
                "path", request.getDescription(false)
            )
        );
    }
}