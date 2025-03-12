package com.hav.hav_imobiliaria.globalExceptionsHandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de validação do @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> Map.of("field", error.getField(), "message", Objects.requireNonNull(error.getDefaultMessage())))
                .toList();

        Map<String, Object> response = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "message", "Erro de validação",
                "errors", errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Captura erros quando o objeto já existe no banco de dados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityException(DataIntegrityViolationException ex) {
        Map<String, Object> response = Map.of(
                "status", HttpStatus.CONFLICT.value(),
                "message", "O recurso já existe no sistema"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Captura erros genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = Map.of(
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "message", "Erro interno no servidor"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

