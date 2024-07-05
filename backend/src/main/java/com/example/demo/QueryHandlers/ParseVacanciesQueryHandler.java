package com.example.demo.QueryHandlers;

import com.example.demo.Exceptions.IncorrectParameter;
import com.example.demo.JSONParsingAdd.VacancyItem;
import com.example.demo.JSONParsingAdd.VacancyResponse;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ParseVacanciesQueryHandler implements Query<Map<String, Object>, List<Vacancy>> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public ResponseEntity<List<Vacancy>> execute(Map<String, Object> input) {

        Integer perPage = (Integer) input.get("perPage");
        String text = (String) input.get("text");
        Integer area = (Integer) input.get("area");
        Integer metro = (Integer) input.get("metro");

        if((perPage < 1) || (perPage > 100)){
            throw new IncorrectParameter("Choose the number between 1 and 100");
        }
        else {
            StringBuilder url = new StringBuilder("https://api.hh.ru/vacancies?per_page=" + perPage);

            if(text != null){
                url.append("&text=").append(text);
            }
            if(area != null){
                url.append("&area=").append(area);
            }
            if(metro != null){
                url.append("&metro=").append(metro);
            }

            VacancyResponse response = restTemplate.getForObject(url.toString(), VacancyResponse.class);

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
                String curr = "RUR";
                try {
                    curr = item.getCurrency();
                } catch (Exception ignored) {

                }
                vacancy.setSalaryCurrency(curr);
                vacancy.setExperience(exp);

                vacancyRepository.save(vacancy);

                vacancies.add(vacancy);
            }

            return ResponseEntity.ok(vacancies);
        }
    }
}
