package ru.dmitry.naujava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ru.dmitry.naujava.security.Role;

import java.time.LocalDate;
import java.util.Set;

/**
 * Пользователь приложения
 */
@Entity
@Table(name = "tbl_user")
public class User {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя пользователя
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Пароль пользователя (хранится в зашифрованном виде)
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Роль пользователя в системе
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    /**
     * Имя пользователя
     */
    @Column(name = "name")
    private String name;

    /**
     * Дата рождения
     */
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    public User() {}

    public User(String username, String password, LocalDate birthdayDate) {
        this.username = username;
        this.password = password;
        this.birthdayDate = birthdayDate;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof User user)) return false;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> role) {
        this.roles = role;
    }
}
