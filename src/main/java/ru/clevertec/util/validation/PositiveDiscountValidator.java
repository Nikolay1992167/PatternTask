package ru.clevertec.util.validation;

import ru.clevertec.exception.ValidationException;

import java.math.BigDecimal;

public class PositiveDiscountValidator extends DiscountValidator {

    @Override
    public void validate(BigDecimal discountPercentage) {

        if (discountPercentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Discount must be positive");
        }
        checkNext(discountPercentage);
    }
}