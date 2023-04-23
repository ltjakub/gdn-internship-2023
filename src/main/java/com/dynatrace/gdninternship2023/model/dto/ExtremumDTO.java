package com.dynatrace.gdninternship2023.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing minimum and maximum values")
public class ExtremumDTO {
    @Schema(description = "Minimum value")
    private Double min;
    @Schema(description = "Maximum value")
    private Double max;
}
