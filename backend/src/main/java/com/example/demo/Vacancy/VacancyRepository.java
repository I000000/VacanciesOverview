package com.example.demo.Vacancy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findBySalaryFromGreaterThanEqual(int salary);

    List<Vacancy> findByNameContaining(String keyword);

    List<Vacancy> findByAreaContaining(String keyword);

    List<Vacancy> findByExperienceContaining(String keyword);
}
