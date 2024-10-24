package ru.dmitry.naujava.service;

import ru.dmitry.naujava.entity.Transaction;

/**
 * Осуществляет операции с доходами и расходами пользователей
 */
public interface TransactionService {
    /**
     * Надуманный метод, зато удобно поработать с тестами на транзакции
     * Сохраняет пачку транзакций в базу данных
     */
    void saveTransacrions(Transaction... transaction);
}
