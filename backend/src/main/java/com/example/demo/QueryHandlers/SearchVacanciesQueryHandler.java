package com.example.demo.QueryHandlers;

import com.example.demo.Exceptions.IncorrectParameter;
import com.example.demo.Vacancy.Vacancy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SearchVacanciesQueryHandler implements Query<Map<String, Object>, List<Vacancy>> {

    @Autowired
    private EntityManager entityManager;

    private final List<String> possibleExperience = Arrays.asList("", "Нет опыта", "От 1 года до 3 лет", "От 3 до 6 лет", "Более 6 лет");

    @Override
    public ResponseEntity<List<Vacancy>> execute(Map<String, Object> input) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vacancy> query = criteriaBuilder.createQuery(Vacancy.class);
        Root<Vacancy> root = query.from(Vacancy.class);
        List<Predicate> predicates = new ArrayList<>();
        String name = (String) input.get("name");
        String area = (String) input.get("area");
        String experience = (String) input.get("experience");
        Integer salary = (Integer) input.get("salary");
        String currency = (String) input.get("currency");

        if(name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if(area != null) {
            predicates.add(criteriaBuilder.like(root.get("area"), "%" + area + "%"));
        }
        if(experience != null) {
            if(possibleExperience.contains(experience)) {
                predicates.add(criteriaBuilder.like(root.get("experience"), "%" + experience + "%"));
            }
            else{
                throw new IncorrectParameter("The experience level you are looking for does not exist. Try a different one.");
            }
        }
        if(salary != null) {
            if(salary > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salaryFrom"), salary));
            }
            else{
                throw new IncorrectParameter("You should make more than nothing.");
            }
        }
        if(currency != null) {
            predicates.add(criteriaBuilder.like(root.get("salaryCurrency"), "%" + currency + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        List<Vacancy> vacancies = entityManager.createQuery(query).getResultList();
        return ResponseEntity.ok(vacancies);
    }
}
