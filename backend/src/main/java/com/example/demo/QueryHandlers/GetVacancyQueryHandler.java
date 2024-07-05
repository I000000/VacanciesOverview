package com.example.demo.QueryHandlers;

import com.example.demo.Exceptions.VacancyNotFound;
import com.example.demo.Vacancy.Vacancy;
import com.example.demo.Vacancy.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetVacancyQueryHandler implements Query<Long, Vacancy> {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public ResponseEntity<Vacancy> execute(Long id) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);

        if(vacancy.isEmpty()){
            throw new VacancyNotFound();
        }

        return ResponseEntity.ok(vacancy.get());
    }
}