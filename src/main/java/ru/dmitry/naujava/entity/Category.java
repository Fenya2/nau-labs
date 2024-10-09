package ru.dmitry.naujava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ru.dmitry.naujava.Constants;

/**
 * Категория доходов/расходов
 */
@Entity
@Table(name = "tbl_category")
public class Category {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя
     */
    @Column(nullable = false)
    private String name;

    /**
     * Описание
     */
    @Column(length = Constants.DESCRIPTION_SIZE)
    private String description;

    /**
     * Категория, в которую текущая вложена. {@code null}, если никуда не вложена
     */
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    /**
     * Создатель категории
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Category() {
    }

    public Category(String name, String description, Category parentCategory, User user) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        this.user = user;
    }

    public long getId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User creator) {
        this.user = creator;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Category category)) return false;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Возвращает строковое представление категории для логирования
     */
    public String asLogString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentCategory=" + parentCategory +
                ", creator=" + user +
                '}';
    }
}
