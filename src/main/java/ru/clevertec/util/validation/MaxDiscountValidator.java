package ru.clevertec.util.validation;

import ru.clevertec.exception.ValidationException;

import java.math.BigDecimal;

public class MaxDiscountValidator extends DiscountValidator {

    @Override
    public void validate(BigDecimal discountPercentage) {
        if (discountPercentage.compareTo(new BigDecimal("50")) > 0) {
            throw new ValidationException("Discount cannot exceed 50%");
        }
        checkNext(discountPercentage);
    }
}