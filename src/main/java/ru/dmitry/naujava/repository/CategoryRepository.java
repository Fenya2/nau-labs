package ru.dmitry.naujava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.entity.User;

import java.util.List;

/**
 * DAO для {@link Category}
 */
@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends CrudRepository<Category, Long> {
    /**
     * Возвращает список корневых категорий (категорий без родительской категории)
     */
    @Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL")
    List<Category> getAllRoots();

    /**
     * Возвращает список категорий, которые непосредственно вложены в передаваемую
     */
    @Query("SELECT c FROM Category c WHERE c.parentCategory = :category")
    List<Category> getNestedOf(Category category);

    /**
     * Возвращает список всех категорий, принадлежащих переданному пользователю
     */
    List<Category> findByUser(User user);
}
