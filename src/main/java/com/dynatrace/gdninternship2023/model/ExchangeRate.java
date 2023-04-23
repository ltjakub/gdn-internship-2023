package com.dynatrace.gdninternship2023.model;

import lombok.Data;

@Data
public class ExchangeRate {

    private String no;
    private String effectiveDate;
    private Double mid;
    private Double bid;
    private Double ask;
}
