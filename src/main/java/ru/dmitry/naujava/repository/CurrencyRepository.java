package ru.dmitry.naujava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dmitry.naujava.entity.Currency;
import org.springframework.stereotype.Repository;

/**
 * DAO {@link Currency валют}
 */
@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
}
