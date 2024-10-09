package ru.dmitry.naujava.service;

import ru.dmitry.naujava.entity.Category;

import java.util.List;
import java.util.Set;

/**
 * Сервис для работы с {@link Category категориями}
 */
public interface CategoryService {

    /**
     * Вкладывает одну категорию в другую.
     *
     * @param parent категория, в которую вкладывается данная. Если {@code null},
     *               то становится корневой.
     * @param child  вкладываемая категория.
     * @throws CategoryException при попытке вложить категорию саму в себя, либо вложить категории друг
     *                           в друга, либо, в общем случае, при попытке замкнуть цикл по вложенности
     *                           (у {@code child} нашлась подкатегория, равная {@code parent})
     */
    public void nestCategory(Category parent, Category child) throws CategoryException;

    /**
     * Возвращает множество категорий, вложенных в данную
     */
    public Set<Category> getNestedCategories(Category category);

    /**
     * Возвращает список категорий, являющихся родительскими для данной. <br>
     * (Категории, у которых в подкатегориях есть переданная (явно или транзитивно))
     *
     * @return список родительских категорий, упорядоченный по дальности от корневой директории
     */
    public List<Category> getParentCategories(Category category);

    /**
     * Сохраняет категорию в базу данных, сохраняя целостность данных в приложении
     * @throws CategoryException если переданную категорию сохранить невозможно
     */
    public void createCategory(Category category) throws CategoryException;

    /**
     * Удаляет категорию и все ее вложенные категории
     */
    public void deleteCategory(Category category);
}
