package ru.clevertec.servicediscount.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.clevertec.servicediscount.DiscountService;

import java.math.BigDecimal;

@Component
public class LoggingDiscountServiceDecorator implements DiscountService {

    private final DiscountService decorated;

    public LoggingDiscountServiceDecorator(@Qualifier("discountServiceImpl") DiscountService decorated) {
        this.decorated = decorated;
    }

    @Override
    public void applyDiscount(BigDecimal discountPercentage) {
        System.out.println("Applying discount: " + discountPercentage + "%.");
        decorated.applyDiscount(discountPercentage);
        System.out.println("Discount applied successfully.");
    }
}