package com.dynatrace.gdninternship2023.util;

import com.dynatrace.gdninternship2023.util.error.ApiError;
import com.dynatrace.gdninternship2023.util.error.ErrorStatus;
import com.dynatrace.gdninternship2023.util.exception.QuotationNotFoundException;
import com.dynatrace.gdninternship2023.util.exception.SeriesSizeExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public final ResponseEntity<Object> handleHttpClientErrorNotFoundException(Exception e) {
        ApiError apiError = new ApiError(ErrorStatus.NOT_FOUND, "Quotation not found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QuotationNotFoundException.class)
    public final ResponseEntity<Object> handleQuotationNotFoundException (Exception e) {
        ApiError apiError = new ApiError(ErrorStatus.NOT_FOUND, "Quotation not found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class, Throwable.class})
    public final ResponseEntity<Object> handleGlobalException(Exception e) {
        ApiError apiError = new ApiError(ErrorStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(SeriesSizeExceededException.class)
        public final ResponseEntity<Object> handleSeriesSizeExceededException (Exception e) {
        ApiError apiError = new ApiError(ErrorStatus.BAD_REQUEST, "Maximum size of 255 data series has been exceeded");
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
