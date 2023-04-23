package com.dynatrace.gdninternship2023.util.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Represents an error response returned by the API")
public class ApiError {
    @Schema(description = "The HTTP status code associated with the error", example = "404")
    private ErrorStatus status;
    @Schema(description = "A message describing the error", example = "Quotation not found")
    private String message;
    @Schema(description = "The timestamp when the error occurred", example = "2023-04-25T15:30:00.000Z")
    private final LocalDateTime timestamp;
    private ApiError() {
        timestamp = LocalDateTime.now();
    }
    public ApiError(ErrorStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
}
