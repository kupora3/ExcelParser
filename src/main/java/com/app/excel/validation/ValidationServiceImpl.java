package com.app.excel.validation;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class ValidationServiceImpl implements ValidationService {
    private static final String REGEX = "^\\d{1,5}$";
    @Override
    public boolean validate(String value) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    @Override
    public int validatePriceValue(String priceValue) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(priceValue);

        if (matcher.find()) {
            return Integer.parseInt(priceValue);
        }
        return 0;
    }
}
