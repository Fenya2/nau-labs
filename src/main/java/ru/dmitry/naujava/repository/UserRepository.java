package ru.dmitry.naujava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dmitry.naujava.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
}
