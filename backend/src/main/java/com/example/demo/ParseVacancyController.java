package com.example.demo;

import com.example.demo.QueryHandlers.ParseVacanciesQueryHandler;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parse")
public class ParseVacancyController {
    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private ParseVacanciesQueryHandler parseVacanciesQueryHandler;

    @GetMapping("/vacancies")
    public ResponseEntity<List<Vacancy>> getVacancies(@RequestParam(value="per_page") Integer perPage,
                                                      @RequestParam(value="text", required=false) String text,
                                                      @RequestParam(value="area", required=false) Integer area,
                                                      @RequestParam(value="metro", required=false) Integer metro) {

        Map<String, Object> input = new HashMap<>();
        input.put("perPage", perPage);
        input.put("text", text);
        input.put("area", area);
        input.put("metro", metro);

        return parseVacanciesQueryHandler.execute(input);
    }
}
