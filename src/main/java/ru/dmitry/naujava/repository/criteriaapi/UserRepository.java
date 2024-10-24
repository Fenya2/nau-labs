package ru.dmitry.naujava.repository.criteriaapi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dmitry.naujava.entity.User;

import java.util.Optional;

/**
 * DAO для работы с пользователями {@link User пользователями}
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
    public Optional<User> findByUsername(String login);
}
