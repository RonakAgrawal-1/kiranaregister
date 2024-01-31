package com.jar.kirana.register.kiranaregister.service;

import com.jar.kirana.register.kiranaregister.exception.CurrencyExchangeRateNotFoundException;
import com.jar.kirana.register.kiranaregister.model.CurrencyApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    @Autowired
    public CurrencyConversionService(RestTemplate restTemplate, @Value("${currency.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            String normalizedBaseCurrency = baseCurrency.toUpperCase();
            String url = apiUrl;
            ResponseEntity<CurrencyApiResponse> responseEntity = restTemplate.getForEntity(url, CurrencyApiResponse.class);
            CurrencyApiResponse response = responseEntity.getBody();

            if (response == null || !response.isSuccess()) {
                throw new CurrencyExchangeRateNotFoundException("Failed to fetch currency exchange rates");
            }

            Map<String, BigDecimal> rates = response.getRates();
            BigDecimal exchangeRate = rates.get(normalizedBaseCurrency);

            if (exchangeRate == null) {
                throw new CurrencyExchangeRateNotFoundException("Exchange rate not found for " + baseCurrency);
            }

            return exchangeRate;
        } catch (RestClientException e) {
            throw new CurrencyExchangeRateNotFoundException("Error while fetching currency exchange rates", e);
        }
    }
}
