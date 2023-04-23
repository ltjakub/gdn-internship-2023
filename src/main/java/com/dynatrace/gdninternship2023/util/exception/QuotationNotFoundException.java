package com.dynatrace.gdninternship2023.util.exception;

public class QuotationNotFoundException extends RuntimeException {
    public QuotationNotFoundException() {
        super("Quotation not found.");
    }
}
