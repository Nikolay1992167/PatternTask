package ru.clevertec.exception;

public class CarDAOException extends RuntimeException {

    public CarDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}