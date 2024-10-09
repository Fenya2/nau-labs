package ru.dmitry.naujava.repository;

import ru.dmitry.naujava.entity.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Дополнительные методы для DAO {@link User пользователя}, которых "нет" в спринговом СRUDRepository
 */
public interface UserRepositoryCustom {
    /**
     * Возвращает пользователей, родившихся раньше переданной даты
     */
    List<User> findByBirthdayDateEarlierThan(LocalDate localDate);

    /**
     * Возвращает пользователей, родившихся позже переданной даты
     */
    List<User> findByBirthdayDateAfter(LocalDate localDate);
}
