package ru.dmitry.naujava.dto.user;

import ru.dmitry.naujava.security.Role;

import java.time.LocalDate;
import java.util.Set;

public record UserDTO(
        int id,
        String login,
        String password,
        Set<Role> role,
        String name,
        LocalDate birthdayDate
) {
}
