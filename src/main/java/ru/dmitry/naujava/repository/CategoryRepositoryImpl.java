package ru.dmitry.naujava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.dmitry.naujava.dblayer.CategoryHolder;
import ru.dmitry.naujava.entity.Category;

import java.util.List;

/**
 * DAO для работы с {@link Category категориями}
 */
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    /**
     * Объект, хранящий категории
     */
    private final CategoryHolder categoryHolder;

    @Autowired
    private CategoryRepositoryImpl(CategoryHolder categoryHolder) {
        this.categoryHolder = categoryHolder;
    }

    /**
     * Сохраняет категорию. Назначает ей идентификатор.
     */
    @Override
    public void create(Category category) {
        categoryHolder.create(category);
    }

    @Override
    public Category read(Long id) {
        return categoryHolder.get(id);
    }

    @Override
    public void update(Category category) {
        categoryHolder.update(category);
    }

    @Override
    public void delete(Long id) {
        categoryHolder.delete(id);
    }

    @Override
    public List<Category> getAllRoots() {
        return categoryHolder.getAll().stream().filter(c -> c.getParentCategory() == null).toList();
    }
}
