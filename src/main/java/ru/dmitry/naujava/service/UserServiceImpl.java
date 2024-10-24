package ru.dmitry.naujava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dmitry.naujava.dto.user.UserCreateDTO;
import ru.dmitry.naujava.dto.user.UserDTO;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.criteriaapi.UserRepository;
import ru.dmitry.naujava.security.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Сервис для работы с пользователями
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        User user = userRepository.findByUsername(login).orElseThrow();
        return userToUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserCreateDTO userDTO) {
        String login = userDTO.login();
        if(userRepository.findByUsername(login).isPresent()) {
            throw new UserCreationException("User with login %s already exists.".formatted(login));
        }
        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        user.setUsername(login);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        userRepository.save(user);
        return userToUserDTO(user);
    }

    private static UserDTO userToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles(),
                user.getName(),
                user.getBirthdayDate()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(), appUser.getPassword(),
                mapRoles(appUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Set<Role> role) {
        return role.stream()
                .map(roleElem -> new SimpleGrantedAuthority("ROLE_" + roleElem))
                .toList();
    }
}
