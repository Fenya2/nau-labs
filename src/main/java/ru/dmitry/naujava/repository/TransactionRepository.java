package ru.dmitry.naujava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dmitry.naujava.entity.Transaction;
import ru.dmitry.naujava.entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * DAO для {@link Transaction}
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    /**
     * Находит все транзакции указанного пользователя
     */
    List<Transaction> findAllByAuthor(User author);

    /**
     * Находит все транзакции с суммой из диапазона [{@code  left}, {@code right}]
     */
    List<Transaction> findAllByAmountBetween(BigDecimal left, BigDecimal right);
}
