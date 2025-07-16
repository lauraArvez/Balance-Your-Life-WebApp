package com.laura.balance.adapter.in.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global para capturar errores de validación de los DTOs
 * enviados en las peticiones HTTP.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura excepciones de validación y devuelve un mapa de campos con errores.
     *
     * @param ex excepción lanzada por @Valid
     * @return mapa con el nombre del campo y su mensaje de error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errores);
    }
}
