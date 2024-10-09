package ru.dmitry.naujava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

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
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Электронная почта пользователя
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Дата рождения
     */
    @Column(name = "birthday_date", nullable = false)
    private LocalDate birthdayDate;

    public User() {}

    public User(String name, String email, LocalDate birthdayDate) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
