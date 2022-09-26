package br.com.apivitor.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
       StandardError error = new StandardError(LocalDate.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());

       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}