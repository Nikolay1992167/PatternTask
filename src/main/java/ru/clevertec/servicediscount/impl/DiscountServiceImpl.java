package ru.clevertec.servicediscount.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.dao.CarDAO;
import ru.clevertec.entity.Car;
import ru.clevertec.observer.Observable;
import ru.clevertec.observer.Observer;
import ru.clevertec.servicediscount.DiscountService;
import ru.clevertec.util.validation.DiscountValidator;
import ru.clevertec.util.validation.MaxDiscountValidator;
import ru.clevertec.util.validation.PositiveDiscountValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService, Observable {

    private final CarDAO carDAO;
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void applyDiscount(BigDecimal discountPercentage) {
        validateDiscount(discountPercentage);
        List<Car> cars = carDAO.getAll(0, Integer.MAX_VALUE);

        for (Car car : cars) {
            BigDecimal originalPrice = car.getPrice();
            BigDecimal discountedPrice = originalPrice
                    .subtract(originalPrice
                            .multiply(discountPercentage)
                            .divide(BigDecimal.valueOf(100)));

            Car updatedCar = new Car(car.getId(), car.getName(), car.getDescription(), discountedPrice, car.getCreated());
            carDAO.update(updatedCar);

            notifyObservers("Price of car " + car.getName() + " changed from " + originalPrice + " to " + discountedPrice);
        }
    }

    private void validateDiscount(BigDecimal discountPercentage) {
        DiscountValidator validator = new PositiveDiscountValidator();
        validator.linkWith(new MaxDiscountValidator());
        validator.validate(discountPercentage);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
            System.out.println(message);
        }
    }
}