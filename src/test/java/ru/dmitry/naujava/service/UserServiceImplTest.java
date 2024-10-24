package ru.dmitry.naujava.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.dmitry.naujava.dto.user.UserCreateDTO;
import ru.dmitry.naujava.dto.user.UserDTO;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.criteriaapi.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserSuccess() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("testLogin", "testPassword");
        User user = new User();
        user.setId(1);
        user.setUsername(userCreateDTO.login());
        user.setPassword(userCreateDTO.password());

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(userCreateDTO);
        Assertions.assertThat(result.login()).isEqualTo("testLogin");
        Assertions.assertThat(result.password()).isEqualTo("testPassword");
    }

    @Test
    void testCreateUserFailureWhenUserExists() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("testLogin", "testPassword");
        User user = new User();
        user.setId(1);
        user.setUsername(userCreateDTO.login());
        user.setPassword(userCreateDTO.password());

        when(userRepository.findByUsername("testLogin")).thenReturn(Optional.of(user));

        Assertions.assertThatThrownBy(() -> userService.createUser(userCreateDTO))
                .isInstanceOf(UserCreationException.class)
                .hasMessage("User with login testLogin already exists.");
    }

    @Test
    void testGetUserByLoginSuccess() {
        User user = new User();
        user.setId(1);
        user.setUsername("testLogin");
        Mockito.when(userRepository.findByUsername("testLogin")).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserByLogin("testLogin");
        Assertions.assertThat(result.login()).isEqualTo("testLogin");
    }

    @Test
    void testGetUserByLoginFailure() {
        Mockito.when(userRepository.findByUsername("testLogin")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> userService.getUserByLogin("testLogin"))
                .isInstanceOf(NoSuchElementException.class);
    }
}
