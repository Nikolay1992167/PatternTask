package ru.clevertec.servicediscount;

import java.math.BigDecimal;

public interface DiscountFacade {

    void saveDiscount(BigDecimal discountPercentage);
    void applyDiscount(BigDecimal discountPercentage);
}