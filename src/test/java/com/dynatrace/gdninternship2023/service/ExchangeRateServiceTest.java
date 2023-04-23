package com.dynatrace.gdninternship2023.service;

import static org.junit.jupiter.api.Assertions.*;

import com.dynatrace.gdninternship2023.model.ExchangeRate;
import com.dynatrace.gdninternship2023.model.ExchangeRates;
import com.dynatrace.gdninternship2023.model.dto.ExtremumDTO;
import com.dynatrace.gdninternship2023.util.exception.QuotationNotFoundException;
import com.dynatrace.gdninternship2023.util.exception.SeriesSizeExceededException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ExchangeRateServiceTest {

    private ExchangeRateService exchangeRateService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        exchangeRateService = new ExchangeRateService(restTemplate);
    }

    private final LocalDate date = LocalDate.of(2023, 4, 22);
    private final String currencyCode = "USD";

    @Test
    public void shouldGetAverageExchangeRate() {
        // given
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s", currencyCode, date);
        ExchangeRates exchangeRates = new ExchangeRates();
        ExchangeRate rate = new ExchangeRate();
        rate.setMid(4.0);
        exchangeRates.setRates(List.of(rate));
        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(exchangeRates);

        // when
        Double result = exchangeRateService.getAverageExchangeRate(date, currencyCode);

        // then
        assertEquals(4.0, result);
    }

    @Test
    public void shouldGetMinAndMaxAverageValue() {
        // given
        String currencyCode = "USD";
        Integer quotations = 5;
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/%d/?format=json", currencyCode, quotations);
        ExchangeRates exchangeRates = new ExchangeRates();
        ExchangeRate rate1 = new ExchangeRate();
        rate1.setMid(1.0);
        ExchangeRate rate2 = new ExchangeRate();
        rate2.setMid(2.0);
        ExchangeRate rate3 = new ExchangeRate();
        rate3.setMid(3.0);
        ExchangeRate rate4 = new ExchangeRate();
        rate4.setMid(4.0);
        ExchangeRate rate5 = new ExchangeRate();
        rate5.setMid(5.0);
        exchangeRates.setRates(List.of(rate1, rate2, rate3, rate4, rate5));
        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(exchangeRates);

        // when
        ExtremumDTO result = exchangeRateService.getMinAndMaxAverageValue(currencyCode, quotations);

        // then
        assertEquals(1.0, result.getMin());
        assertEquals(5.0, result.getMax());
    }

    @Test
    public void shouldGetHighestDifferenceInBuyAskRate() {
        // given
        String currencyCode = "USD";
        Integer quotations = 3;
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/%d/?format=json", currencyCode, quotations);
        ExchangeRates exchangeRates = new ExchangeRates();
        ExchangeRate rate1 = new ExchangeRate();
        rate1.setAsk(4.0);
        rate1.setBid(2.0);
        ExchangeRate rate2 = new ExchangeRate();
        rate2.setAsk(4.1);
        rate2.setBid(2.3);
        ExchangeRate rate3 = new ExchangeRate();
        rate3.setAsk(4.7);
        rate3.setBid(3.0);
        exchangeRates.setRates(Arrays.asList(rate1, rate2, rate3));
        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(exchangeRates);

        // when
        Double result = exchangeRateService.getHighestDifferenceInBuyAskRate(currencyCode, quotations);

        // then
        assertEquals(2.0, result);
    }

    @Test
    public void shouldGetAverageExchangeRateThrowQuotationNotFoundException() {
        // given
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s", currencyCode, date);

        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(null);

        // then
        assertThrows(QuotationNotFoundException.class, () -> exchangeRateService.getAverageExchangeRate(date, currencyCode));
    }

    @Test
    public void shouldGetMinAndMaxAverageValueThrowQuotationNotFoundException() {
        // given
        Integer quotations = 3;
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/%d/?format=json", currencyCode, quotations);

        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(null);

        // then
        assertThrows(QuotationNotFoundException.class, () -> exchangeRateService.getMinAndMaxAverageValue(currencyCode, quotations));
    }

    @Test
    public void shouldGetHighestDifferenceInBuyAskRateThrowQuotationNotFoundException() {
        // given
        Integer quotations = 3;
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/%d/?format=json", currencyCode, quotations);

        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(null);

        // then
        assertThrows(QuotationNotFoundException.class, () -> exchangeRateService.getHighestDifferenceInBuyAskRate(currencyCode, quotations));
    }

    @Test
    public void shouldGetHighestDifferenceInBuyAskRateThrowSeriesSizeExceededException() {
        // given
        Integer quotations = 256;
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/%d/?format=json", currencyCode, quotations);

        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(null);

        // then
        assertThrows(SeriesSizeExceededException.class, () -> exchangeRateService.getHighestDifferenceInBuyAskRate(currencyCode, quotations));
    }

    @Test
    public void shouldNotThrowSeriesSizeExceededException() {
        // given
        String currencyCode = "USD";
        Integer quotations = 255;
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/%d/?format=json", currencyCode, quotations);
        ExchangeRates exchangeRates = new ExchangeRates();
        ExchangeRate rate1 = new ExchangeRate();
        rate1.setMid(1.0);

        exchangeRates.setRates(List.of(rate1));
        when(restTemplate.getForObject(url, ExchangeRates.class)).thenReturn(exchangeRates);

        //then
        assertDoesNotThrow(() -> exchangeRateService.getMinAndMaxAverageValue(currencyCode, quotations));
    }
}