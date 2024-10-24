package ru.dmitry.naujava.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dmitry.naujava.dto.user.CreateUserDTO;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.criteriaapi.UserRepository;
import ru.dmitry.naujava.security.UserRole;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(CreateUserDTO createUserDTO) throws UserExistException {
        final String username = Objects.requireNonNull(createUserDTO.username());
        final String password = Objects.requireNonNull(createUserDTO.password());
        final UserRole userRole = createUserDTO.userRole();

        userRepository.findByUsername(username).ifPresent(user -> {
                    throw new UserExistException("user %s already exists".formatted(username));
                }
        );
        final User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Set.of(Objects.requireNonNullElse(userRole, UserRole.USER)));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(
                appUser.getUserName(),
                appUser.getPassword(),
                mapRoles(appUser.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> mapRoles(Set<UserRole> role) {
        return role.stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .toList();
    }
}
