package ru.dmitry.naujava.repository;

import ru.dmitry.naujava.entity.Category;

import java.util.List;

/**
 * DAO для {@link Category}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    /**
     * Возвращает список корневых категорий
     */
    List<Category> getAllRoots();
}
