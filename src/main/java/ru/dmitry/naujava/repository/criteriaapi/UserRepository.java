package ru.dmitry.naujava.repository.criteriaapi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dmitry.naujava.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
}
