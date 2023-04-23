package com.dynatrace.gdninternship2023.controller;

import static org.mockito.Mockito.when;

import com.dynatrace.gdninternship2023.model.dto.ExtremumDTO;
import com.dynatrace.gdninternship2023.service.NbpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@WebMvcTest(NbpController.class)
public class NbpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NbpService nbpService;

    @Test
    public void shouldStatusBeOkForGetAverageExchangeRate() throws Exception {
        // given
        String currencyCode = "USD";
        LocalDate date = LocalDate.now();
        Double expectedRate = 4.2;

        when(nbpService.getAverageExchangeRate(date, currencyCode)).thenReturn(expectedRate);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nbp/{currencyCode}/{date}", currencyCode, date)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ShouldStatusAndValuesBeOkGetMinAndMaxAverageValue() throws Exception {
        // given
        String currencyCode = "USD";
        Integer quotations = 3;
        ExtremumDTO extremumDTO = new ExtremumDTO();
        extremumDTO.setMin(3.0);
        extremumDTO.setMax(4.0);

        when(nbpService.getMinAndMaxAverageValue(currencyCode, quotations)).thenReturn(extremumDTO);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nbp/{currencyCode}/last/{quotations}/extremum", currencyCode, quotations)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").value(extremumDTO.getMin()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.max").value(extremumDTO.getMax()));
    }

    @Test
    public void shouldStatusBeOkForGetHighestDifferenceInBuyAskRate() throws Exception {
        // given
        String currencyCode = "USD";
        Integer quotations = 3;
        Double highestDifference = 2.0;

        when(nbpService.getHighestDifferenceInBuyAskRate(currencyCode, quotations)).thenReturn(highestDifference);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nbp/{currencyCode}/last/{quotations}/difference", currencyCode, quotations)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}