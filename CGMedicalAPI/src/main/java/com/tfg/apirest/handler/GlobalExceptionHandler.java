package com.tfg.apirest.handler;

import com.tfg.apirest.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException elementoNoEncontrado, WebRequest request) {
        log.error("[ERROR] Elemento no encontrado.");
        return buildErrorResponse(elementoNoEncontrado.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("[ERROR] Formato de la petición incorrecto.");
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException badCredentials, WebRequest request) {
        log.error("[ERROR] Credenciales incorrectas.");
        return buildErrorResponse(badCredentials.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleForbiddenException(AccessDeniedException forbidden, WebRequest request) {
        log.error("[ERROR] Sin permisos para ejecutar operación.");
        return buildErrorResponse(forbidden.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationExceptionException(ConstraintViolationException ex, WebRequest request) {
        log.error("[ERROR] Error de validación de algún parámetro.");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Error de validación de algún parámetro.");
        ex.getConstraintViolations().forEach(c ->
           errorResponse.addValidationError(ex.getMessage().split(":")[0] , c.getMessageTemplate())
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                           @NotNull HttpHeaders headers,
                                                                           @NotNull HttpStatus status,
                                                                           @NotNull WebRequest request) {
        log.error("[ERROR] Error de validación de algún parámetro.");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Error de validación de algún parámetro.");
        ex.getBindingResult().getFieldErrors().forEach( fieldError ->
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }



//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Object> handleAllUncaughtException(Exception exception) {
//        log.error(exception.getMessage());
//        return buildErrorResponse("Internal Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    private ResponseEntity<Object> buildErrorResponse(String exceptionMessage,
                                                      HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exceptionMessage);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}