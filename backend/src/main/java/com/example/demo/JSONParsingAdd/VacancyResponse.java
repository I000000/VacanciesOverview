package com.example.demo.JSONParsingAdd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyResponse {
    private List<VacancyItem> items;
}