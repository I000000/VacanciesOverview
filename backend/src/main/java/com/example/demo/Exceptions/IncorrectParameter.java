package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectParameter extends CustomBaseException{
    public IncorrectParameter(String message) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message));
    }
}
