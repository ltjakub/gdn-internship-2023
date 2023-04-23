package com.dynatrace.gdninternship2023.util.exception;

public class SeriesSizeExceededException extends RuntimeException {
    public SeriesSizeExceededException() {
        super("Maximum size of 255 data series has been exceeded.");
    }
}
