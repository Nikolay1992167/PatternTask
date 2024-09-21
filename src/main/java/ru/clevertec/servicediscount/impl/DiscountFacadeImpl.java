package ru.clevertec.servicediscount.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.servicediscount.DiscountFacade;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DiscountFacadeImpl implements DiscountFacade {

    private final DiscountSaveService discountSaveService;
    private final LoggingDiscountServiceDecorator discountServiceImpl;

    @Override
    public void saveDiscount(BigDecimal discountPercentage) {
        discountSaveService.addDiscount(discountPercentage);
    }

    @Override
    public void applyDiscount(BigDecimal discountPercentage) {
        discountServiceImpl.applyDiscount(discountPercentage);
    }
}