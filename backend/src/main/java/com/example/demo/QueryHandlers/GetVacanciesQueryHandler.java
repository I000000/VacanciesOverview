package com.example.demo.QueryHandlers;

import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetVacanciesQueryHandler implements Query<Void, List<Vacancy>> {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public ResponseEntity<List<Vacancy>> execute(Void input) {
        return ResponseEntity.ok(vacancyRepository.findAll());
    }
}
