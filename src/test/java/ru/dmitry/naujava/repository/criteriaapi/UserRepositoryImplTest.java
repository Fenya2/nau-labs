package ru.dmitry.naujava.repository.criteriaapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.dmitry.naujava.entity.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryImplTest {

    private final UserRepository userRepository;
    private static List<User> users;

    @Autowired
    public UserRepositoryImplTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        users = List.of(
                new User("1", "1", LocalDate.of(2024, 1, 1)),
                new User("2", "2", LocalDate.of(2004, 1, 1)),
                new User("3", "3", LocalDate.of(1984, 1, 1)));
    }

    @Test
    void testFindByBirthdayDateEarlierThan() {
        userRepository.saveAll(users);
        List<User> users = userRepository.findByBirthdayDateEarlierThan(LocalDate.of(2014, 1, 1));
        assertThat(users).containsExactlyElementsOf(UserRepositoryImplTest.users.subList(1, 3));
    }

    @Test
    void testFindByBirthdayDateAfter() {
        userRepository.saveAll(users);
        List<User> users = userRepository.findByBirthdayDateAfter(LocalDate.of(1994, 1, 1));
        assertThat(users).containsExactlyElementsOf(UserRepositoryImplTest.users.subList(0, 2));
    }

    @Test
    void testFindByUsernameSuccess() {
        userRepository.save(users.getFirst());
        User user = userRepository.findByUsername("1").orElseThrow();
        assertThat(user).isNotNull();
    }

    @Test
    void testFindByUsernameFailure() {
        assertThat(userRepository.findByUsername("1")).isEmpty();
    }
}