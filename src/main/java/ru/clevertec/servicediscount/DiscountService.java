package ru.clevertec.servicediscount;

import java.math.BigDecimal;

public interface DiscountService {

    void applyDiscount(BigDecimal discountPercentage);
}