package com.skaotico.servicio.rest.common.exception;

import com.skaotico.servicio.rest.common.dto.ApiResponseGeneric;
import com.skaotico.servicio.rest.common.factory.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleEmptyResult(
            EmptyResultDataAccessException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseFactory.error("Registro no encontrado en la base de datos", request, "NOT_FOUND"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseFactory.error("Error de integridad en la base de datos (violación de constraints)", request, "CONFLICT"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleException(
            Exception ex, HttpServletRequest request) {

        ex.printStackTrace(); // Idealmente reemplazar con logger
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseFactory.error("Error interno del servidor", request, "INTERNAL_ERROR"));
    }
}
