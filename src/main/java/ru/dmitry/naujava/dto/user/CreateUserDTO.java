package ru.dmitry.naujava.dto.user;

import ru.dmitry.naujava.security.UserRole;

public record CreateUserDTO(
        String username,
        String password,
        UserRole userRole
) {
}
