package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBaseException.class)
    public ResponseEntity<SimpleResponse> handVacancyNotFoundException(CustomBaseException e){
        return ResponseEntity.status(e.getStatus()).body(e.getSimpleResponse());
    }
}
