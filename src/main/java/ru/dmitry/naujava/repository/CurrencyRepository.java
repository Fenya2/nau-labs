package ru.dmitry.naujava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dmitry.naujava.entity.Currency;
import org.springframework.stereotype.Repository;

/**
 * DAO {@link Currency валют}
 */
@Repository
@RepositoryRestResource
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
}
