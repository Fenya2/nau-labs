package ru.dmitry.naujava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dmitry.naujava.entity.Transaction;
import ru.dmitry.naujava.entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * DAO для {@link Transaction}
 */
@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    /**
     * Находит все транзакции указанного пользователя
     */
    @Query("SELECT t FROM Transaction t WHERE t.author = :author")
    List<Transaction> findAllByAuthor(@Param("author") User author);

    /**
     * Находит все транзакции с суммой из диапазона [{@code  left}, {@code right}]
     */
    List<Transaction> findAllByAmountBetween(BigDecimal left, BigDecimal right);
}
