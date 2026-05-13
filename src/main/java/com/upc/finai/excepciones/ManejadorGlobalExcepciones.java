package com.upc.finai.excepciones;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RespuestaError> manejarRuntime(RuntimeException ex) {
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                List.of()
        );
        return ResponseEntity.badRequest().body(respuestaError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaError> manejarValidacion(MethodArgumentNotValidException ex) {
        List<String> detalles = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                detalles
        );
        return ResponseEntity.badRequest().body(respuestaError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RespuestaError> manejarRestricciones(ConstraintViolationException ex) {
        RespuestaError respuestaError = new RespuestaError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Violación de restricciones",
                ex.getConstraintViolations().stream().map(v -> v.getMessage()).toList()
        );
        return ResponseEntity.badRequest().body(respuestaError);
    }
}
