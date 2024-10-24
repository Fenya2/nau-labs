package ru.dmitry.naujava.service;

import ru.dmitry.naujava.dto.user.UserCreateDTO;
import ru.dmitry.naujava.dto.user.UserDTO;

public interface UserService {

    /**
     * Возвращает пользователя по его идентификатору
     * @param login
     * @return
     */
    UserDTO getUserByLogin(String login);


    /**
     * Создает пользователя
     */
    UserDTO createUser(UserCreateDTO userDTO);
}
