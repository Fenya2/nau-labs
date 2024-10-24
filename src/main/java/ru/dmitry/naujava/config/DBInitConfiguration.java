package ru.dmitry.naujava.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dmitry.naujava.dto.user.CreateUserDTO;
import ru.dmitry.naujava.security.UserRole;
import ru.dmitry.naujava.service.UserService;

/**
 * Заполняет базу данных перед стартом приложения необходимыми данными
 */
@Component
public class DBInitConfiguration {

    private final UserService userService;

    public DBInitConfiguration(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создает в БД администратора
     */
    @EventListener(ApplicationReadyEvent.class)
    public void createAdmin() {
        userService.createUser(new CreateUserDTO("admin", "admin", UserRole.ADMIN));
    }
}
