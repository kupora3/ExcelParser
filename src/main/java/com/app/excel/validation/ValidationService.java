package com.app.excel.validation;

public interface ValidationService {
    boolean validate(String value);

    int validatePriceValue(String priceValue);
}
