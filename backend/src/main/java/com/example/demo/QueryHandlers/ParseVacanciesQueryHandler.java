package com.example.demo.QueryHandlers;

import com.example.demo.Exceptions.IncorrectParameter;
import com.example.demo.JSONParsingAdd.VacancyItem;
import com.example.demo.JSONParsingAdd.VacancyResponse;
import com.example.demo.Query;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParseVacanciesQueryHandler implements Query<Integer, List<Vacancy>> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public ResponseEntity<List<Vacancy>> execute(Integer perPage) {

        if((perPage < 1) || (perPage > 100)){
            throw new IncorrectParameter("Choose the number between 1 and 100");
        }
        else {

            String url = "https://api.hh.ru/vacancies?per_page=" + perPage;

            VacancyResponse response = restTemplate.getForObject(url, VacancyResponse.class);

            List<Vacancy> vacancies = new ArrayList<>();
            assert response != null;
            for (VacancyItem item : response.getItems()) {
                Vacancy vacancy = new Vacancy();
                vacancy.setId(item.getId());
                vacancy.setName(item.getName());
                vacancy.setArea(item.getArea());
                int salaryFrom = 0;
                try {
                    salaryFrom = item.getSalaryFrom();
                } catch (Exception ignored) {

                }
                vacancy.setSalaryFrom(salaryFrom);
                int salaryTo = 0;
                try {
                    salaryTo = item.getSalaryTo();
                } catch (Exception ignored) {

                }
                vacancy.setSalaryTo(salaryTo);
                String exp = "-";
                try {
                    exp = item.getExperience();
                } catch (Exception ignored) {

                }
                vacancy.setExperience(exp);

                vacancyRepository.save(vacancy);

                vacancies.add(vacancy);
            }

            return ResponseEntity.ok(vacancies);
        }
    }
}
