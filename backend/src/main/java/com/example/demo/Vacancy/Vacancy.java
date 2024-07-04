package com.example.demo.Vacancy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacancy {
    @Id
    private long id;

    private String name;

    private String area;

    private String experience;

    private int salaryFrom;

    private int salaryTo;

    private String salaryCurrency;
}
