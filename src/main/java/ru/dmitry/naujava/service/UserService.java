package ru.dmitry.naujava.service;

import ru.dmitry.naujava.dto.user.CreateUserDTO;

/**
 * Сервис для работы с пользователями
 */
public interface UserService {
    /**
     * Создает пользователя в системе. Если роль не передана,
     * устанавливает {@link ru.dmitry.naujava.security.UserRole#USER}
     */
    void createUser(CreateUserDTO createUserDTO) throws UserExistException;
}
