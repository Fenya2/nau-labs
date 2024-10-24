package ru.dmitry.naujava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ru.dmitry.naujava.security.UserRole;

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
     * Имя пользователя (login)
     */
    @Column(name = "name", unique = true)
    private String username;

    /**
     * Пароль пользователя (в зашифрованном виде)
     */
    @Column(name = "password")
    private String password;

    /**
     * Роли, которыми обладает пользователь
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Set<UserRole> role;

    /**
     * Электронная почта пользователя
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Дата рождения
     */
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    public User() {
    }

    public User(String name, String email, LocalDate birthdayDate) {
        this.username = name;
        this.email = email;
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

    public String getUserName() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRole() {
        return role;
    }

    public void setRole(Set<UserRole> role) {
        this.role = role;
    }
}
