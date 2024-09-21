package ru.clevertec.exception;

public class JDBCConnectionException extends RuntimeException {

    public JDBCConnectionException(String message) {
        super("Error connecting to the database:" + message);
    }
}