package ru.clevertec.servicediscount.impl;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class DiscountSaveService {

    private final Map<LocalDate, BigDecimal> discountMap;

    public DiscountSaveService() {
        this.discountMap = new HashMap<>();
    }

    public void addDiscount(BigDecimal discountPercentage) {
        discountMap.put(LocalDate.now(), discountPercentage);
    }
}