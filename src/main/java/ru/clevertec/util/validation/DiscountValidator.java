package ru.clevertec.util.validation;

import java.math.BigDecimal;

public abstract class DiscountValidator {

    private DiscountValidator next;

    public void linkWith(DiscountValidator next) {
        this.next = next;
    }

    public abstract void validate(BigDecimal discountPercentage);

    public void checkNext(BigDecimal discountPercentage) {
        if (next != null) {
            next.validate(discountPercentage);
        }
    }
}