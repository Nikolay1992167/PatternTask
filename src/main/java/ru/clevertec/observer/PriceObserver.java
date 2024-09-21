package ru.clevertec.observer;

public class PriceObserver implements Observer {

    @Override
    public void update(String message) {
        System.out.println("PriceObserver: " + message);
    }
}