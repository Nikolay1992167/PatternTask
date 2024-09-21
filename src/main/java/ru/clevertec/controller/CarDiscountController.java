package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.servicediscount.DiscountFacade;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount/cars")
public class CarDiscountController {

    private final DiscountFacade discountFacade;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getAll(@RequestParam BigDecimal discountPercentage) {
        discountFacade.saveDiscount(discountPercentage);
        discountFacade.applyDiscount(discountPercentage);
    }
}