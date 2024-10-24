package ru.dmitry.naujava.service;

/**
 * Исключение, возникающее при работе с категориями
 */
public class CategoryException extends RuntimeException {
    public CategoryException(String message) {
        super(message);
    }
}
