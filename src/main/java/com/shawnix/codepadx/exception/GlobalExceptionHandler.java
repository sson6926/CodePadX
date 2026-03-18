package com.shawnix.codepadx.exception;

import com.shawnix.codepadx.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(exception = RuntimeException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(ApiResponse
                        .builder()
                        .code(400)
                        .data(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(exception = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.builder().code(exception.getErrorCode().getCode()).data(exception.getErrorCode().getMessage()).build());

    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return ResponseEntity.badRequest().body(ApiResponse.builder().code(400).data(errorMessage).build());
    }
}
