package com.example.demo.JSONParsingAdd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Salary {
    private int from;
    private int to;
    private String currency;
}
