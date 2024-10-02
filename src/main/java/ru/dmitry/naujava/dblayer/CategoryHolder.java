package ru.dmitry.naujava.dblayer;

import org.springframework.stereotype.Component;
import ru.dmitry.naujava.entity.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Хранит {@link ru.dmitry.naujava.entity.Category категории} в {@link java.util.Map}.
 */
@Component
public class CategoryHolder {
    /**
     * Категории, присутствующие в программе.
     */
    private final Map<Long, Category> categories = new HashMap<>();

    /**
     * Очередной идентификатор добавляемой категории.
     */
    private long nextId = 1;

    /**
     * Сохраняет категорию. Назначает ей идентификатор
     */
    public void create(Category category) {
        long id = nextId++;
        categories.put(id, category);
        category.setId(id);
    }

    /**
     * Возвращает категорию по ее идентификатору.
     *
     * @return найденная категория. {@code null}, если категории с переданным идентификатором не существует
     */
    public Category get(long id) {
        return categories.get(id);
    }

    /**
     * Обновляет категорию. Если категории с заданным идентификатором не существует, создает ее.
     */
    public void update(Category category) {
        categories.put(category.getId(), category);
    }

    /**
     * Удаляет категорию по ее идентификатору
     */
    public void delete(long id) {
        categories.remove(id);
    }

    /**
     * Возвращает все категории в приложении
     */
    public List<Category> getAll() {
        return new ArrayList<>(categories.values());
    }
}
