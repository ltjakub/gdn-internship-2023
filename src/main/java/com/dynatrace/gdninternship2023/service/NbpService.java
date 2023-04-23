package com.dynatrace.gdninternship2023.service;

import com.dynatrace.gdninternship2023.model.ExchangeRate;
import com.dynatrace.gdninternship2023.model.ExchangeRates;
import com.dynatrace.gdninternship2023.model.dto.ExtremumDTO;
import com.dynatrace.gdninternship2023.util.exception.QuotationNotFoundException;
import com.dynatrace.gdninternship2023.util.exception.SeriesSizeExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NbpService {

    private final RestTemplate restTemplate;
    private final Integer MAX_QUOTATIONS = 255;

    public Double getAverageExchangeRate(LocalDate date, String currencyCode) {
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s", currencyCode, date);
        ExchangeRates exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);
        checkIfExchangeRatesIsNotNull(exchangeRates);
        return exchangeRates.getRates().get(0).getMid();
    }

    public ExtremumDTO getMinAndMaxAverageValue(String currencyCode, Integer quotations) {
        checkIfQuotationsAreInRange(quotations);
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/%d/?format=json", currencyCode, quotations);
        ExchangeRates exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);
        checkIfExchangeRatesIsNotNull(exchangeRates);
        double min = exchangeRates.getRates().get(0).getMid();
        double max = exchangeRates.getRates().get(0).getMid();
        for (ExchangeRate rate : exchangeRates.getRates()) {
            if (rate.getMid() > max)
                max = rate.getMid();
            if (rate.getMid() < min)
                min = rate.getMid();
        }
        return new ExtremumDTO(min, max);
    }

    public Double getHighestDifferenceInBuyAskRate(String currencyCode, Integer quotations) {
        checkIfQuotationsAreInRange(quotations);
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/%d/?format=json", currencyCode, quotations);
        ExchangeRates exchangeRates = restTemplate.getForObject(url, ExchangeRates.class);
        checkIfExchangeRatesIsNotNull(exchangeRates);
        List<Double> differences = exchangeRates.getRates().stream().map(t -> t.getAsk() - t.getBid()).toList();
        return Collections.max(differences);
    }

    private void checkIfExchangeRatesIsNotNull(ExchangeRates exchangeRates) {
        if (exchangeRates == null) {
            throw new QuotationNotFoundException();
        }
    }

    private void checkIfQuotationsAreInRange(Integer quotations) {
        if (quotations > MAX_QUOTATIONS) {
            throw new SeriesSizeExceededException();
        }
    }
}
