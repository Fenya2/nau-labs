package ru.dmitry.naujava.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Категория дохода/расхода*
 */
public class Category {
    /**
     * Идентификатор
     */
    private long id;

    /**
     * Имя
     */
    private String name;

    /**
     * Описание
     */
    private String description;

    /**
     * Категория, в которую текущая вложена. {@code null}, если никуда не вложена
     */
    private Category parentCategory;

    /**
     * Множество подкатегорий данной категории
     */
    private Set<Category> nested = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Set<Category> getNested() {
        return nested;
    }

    public void setNested(Set<Category> nested) {
        this.nested = Objects.requireNonNullElseGet(nested, HashSet::new);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Category category)) return false;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
                ", children=" + nested +
                '}';
    }
}
