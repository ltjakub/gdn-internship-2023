package com.dynatrace.gdninternship2023.controller;

import com.dynatrace.gdninternship2023.model.dto.ExtremumDTO;
import com.dynatrace.gdninternship2023.service.NbpService;
import com.dynatrace.gdninternship2023.util.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nbp")
public class NbpController {

    private final NbpService nbpService;

    @Operation(summary = "Get average exchange rate for a given currency code and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Maximum size of 255 data series has been exceeded",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Quotation not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{currencyCode}/{date}")
    public Double getAverageExchangeRate(@Parameter(
            name = "currencyCode",
            description = "The currency code (e.g. USD, EUR)",
            example = "EUR"
    ) @PathVariable String currencyCode, @Parameter(
            name = "date",
            description = "The date in the format yyyy-mm-dd",
            example = "2022-04-04"
    ) @PathVariable LocalDate date) {
        return nbpService.getAverageExchangeRate(date, currencyCode);
    }

    @Operation(
            summary = "Get minimum and maximum average value",
            description = "Get the minimum and maximum average value for a given currency and number of quotations"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Maximum size of 255 data series has been exceeded",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Quotation not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{currencyCode}/last/{quotations}/extremum")
    public ResponseEntity<ExtremumDTO> getMinAndMaxAverageValue(
            @Parameter(
                    name = "currencyCode",
                    description = "The currency code (e.g. USD, EUR)",
                    example = "EUR"
            )
            @PathVariable String currencyCode,
            @Parameter(
                    name = "quotations",
                    description = "Integers that represent number of last quotations",
                    example = "5"
            )
            @PathVariable Integer quotations
    ) {
        ExtremumDTO extremum = nbpService.getMinAndMaxAverageValue(currencyCode, quotations);
        return ResponseEntity.ok(extremum);
    }

    @Operation(
            summary = "Get highest difference in buy and ask rate",
            description = "Get the highest difference between buy and ask rate for a given currency and number of quotations"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Maximum size of 255 data series has been exceeded",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Quotation not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{currencyCode}/last/{quotations}/difference")
    public ResponseEntity<Double> getHighestDifferenceInBuyAskRate(
            @Parameter(
                    name = "currencyCode",
                    description = "The currency code (e.g. USD, EUR)",
                    example = "EUR"
            )
            @PathVariable String currencyCode,
            @Parameter(
                    name = "quotations",
                    description = "Integers that represent number of last quotations",
                    example = "5"
            )
            @PathVariable Integer quotations
    ) {
        Double highestDifference = nbpService.getHighestDifferenceInBuyAskRate(currencyCode, quotations);
        return ResponseEntity.ok(highestDifference);
    }
}
