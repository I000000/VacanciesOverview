package com.example.demo.JSONParsingAdd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class VacancyItem {
    private long id;
    private String name;
    private Area area;
    private Salary salary;
    private Experience experience;

    public VacancyItem(long id, String name, String area, int salaryFrom, int salaryTo, String curr, String exp){
        this.id = id;
        this.name = name;
        this.area = new Area();
        this.area.setName(area);
        this.salary = new Salary();
        this.salary.setFrom(salaryFrom);
        this.salary.setTo(salaryTo);
        this.salary.setCurrency(curr);
        this.experience = new Experience();
        this.experience.setName(exp);
    }

    public String getArea() {
        return area.getName();
    }

    public int getSalaryFrom() {
        return salary.getFrom();
    }

    public int getSalaryTo() {
        return salary.getTo();
    }

    public String getCurrency(){
        return salary.getCurrency();
    }

    public String getExperience() {
        return experience.getName();
    }
}
