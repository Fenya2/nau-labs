package ru.dmitry.naujava.service;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}