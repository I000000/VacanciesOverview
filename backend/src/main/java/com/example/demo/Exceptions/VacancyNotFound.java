package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;

public class VacancyNotFound extends CustomBaseException{

    public VacancyNotFound() {
        super(HttpStatus.NOT_FOUND, new SimpleResponse("Vacancy Not Found"));
    }
}
