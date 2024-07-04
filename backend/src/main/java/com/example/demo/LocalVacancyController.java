package com.example.demo;

import com.example.demo.Exceptions.VacancyNotFound;
import com.example.demo.QueryHandlers.GetVacanciesQueryHandler;
import com.example.demo.QueryHandlers.SearchVacanciesQueryHandler;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import com.example.demo.QueryHandlers.GetVacancyQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/local/vacancies")
public class LocalVacancyController {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private GetVacanciesQueryHandler getVacanciesQueryHandler;

    @Autowired
    private GetVacancyQueryHandler getVacancyQueryHandler;

    @Autowired
    private SearchVacanciesQueryHandler searchVacanciesQueryHandler;

    @GetMapping
    public ResponseEntity<List<Vacancy>> getVacancies() {
        return getVacanciesQueryHandler.execute(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancy(@PathVariable Long id) {
        return getVacancyQueryHandler.execute(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vacancy>> searchVacancies(
            @RequestParam(value="name", required=false) String name,
            @RequestParam(value="area", required=false) String area,
            @RequestParam(value="experience", required=false) String experience,
            @RequestParam(value="salary", required=false) Integer salary,
            @RequestParam(value="currency", required=false) String currency){

        Map<String, Object> input = new HashMap<>();
        input.put("name", name);
        input.put("area", area);
        input.put("experience", experience);
        input.put("salary", salary);
        input.put("currency", currency);

        return searchVacanciesQueryHandler.execute(input);
    }
}
