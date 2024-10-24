package ru.dmitry.naujava.service;

public class UserExistException extends UserServiceException {
    public UserExistException(String message) {
        super(message);
    }
}
