package root.validation;

public interface ValidationService {
    boolean validate(String value);

    int validatePriceValue(String priceValue);
}
