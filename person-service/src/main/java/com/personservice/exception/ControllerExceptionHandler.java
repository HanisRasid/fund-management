package com.personservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler{
    
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> personNotFoundException(PersonNotFoundException exception){
        ErrorResponse msg = new ErrorResponse(HttpStatus.NOT_FOUND, LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<ErrorResponse>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception exception) {
      ErrorResponse msg = new ErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR,
          LocalDateTime.now(),
          exception.getMessage());
      
      return new ResponseEntity<ErrorResponse>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
