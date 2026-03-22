package com.shawnix.codepadx.exception;

import com.shawnix.codepadx.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(exception = RuntimeException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse
                        .builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .data(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(exception = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        int statusCode = exception.getErrorCode().getCode();
        HttpStatus status = HttpStatus.resolve(statusCode);
        if (status == null) {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .code(status.value())
                        .message(status.getReasonPhrase())
                        .data(exception.getErrorCode().getMessage())
                        .build()
        );

    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .data(errorMessage)
                        .build()
        );
    }
}
