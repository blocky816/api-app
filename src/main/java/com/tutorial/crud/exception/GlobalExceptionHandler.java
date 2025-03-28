package com.tutorial.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Resource not found exception response entity.
     *
     * @param ex the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentReferenceException.class)
    public ResponseEntity<?> paymentReferenceException(Exception ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.toString() ,ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejo de excepción cuando el cliente no se encuentra
    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<?> clienteNoEncontradoException(ClienteNoEncontradoException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateTimeException.class)
    public ResponseEntity<?> handleInvalidDateTimeException(InvalidDateTimeException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepción cuando no se puede enviar el correo
    @ExceptionHandler(CorreoEnvioException.class)
    public ResponseEntity<?> correoEnvioException(CorreoEnvioException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejo de excepción cuando el cliente no se encuentra
    @ExceptionHandler(ChatGPTException.class)
    public ResponseEntity<?> ChatGPTException(ChatGPTException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Globle excpetion handler response entity.
     *
     * @param ex the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.toString() ,ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
