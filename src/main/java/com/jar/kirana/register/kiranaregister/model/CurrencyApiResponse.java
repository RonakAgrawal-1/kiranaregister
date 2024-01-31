package com.jar.kirana.register.kiranaregister.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyApiResponse {
    private boolean success;
    private Map<String, BigDecimal> rates;
}
