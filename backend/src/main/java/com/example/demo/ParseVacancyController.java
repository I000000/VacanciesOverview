package com.example.demo;

import com.example.demo.QueryHandlers.ParseVacanciesQueryHandler;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parse")
public class ParseVacancyController {
    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private ParseVacanciesQueryHandler parseVacanciesQueryHandler;

    @GetMapping("/vacancies/{num}")
    public ResponseEntity<List<Vacancy>> getVacancies(@PathVariable Integer num) {
        return parseVacanciesQueryHandler.execute(num);
    }
}
