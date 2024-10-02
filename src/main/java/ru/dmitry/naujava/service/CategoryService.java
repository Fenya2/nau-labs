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
     * @param parentCategory категория, в которую вкладывается данная. Если {@code null},
     *                       то становится корневой.
     * @param childCategory  вкладываемая категория.
     * @throws CategoryException при попытке вложить категорию саму в себя, либо вложить категории друг
     *                           в друга, либо, в общем случае, при попытке замкнуть цикл по вложенности
     *                           (у данной категории нашлась подкатегория, в которую вложена данная)
     */
    public void nestCategory(Category parentCategory, Category childCategory) throws CategoryException;

    /**
     * Возвращает множество категорий, вложенных в данную
     */
    public Set<Category> getNestedCategories(Category category);

    /**
     * Возвращает список категорий, являющихся родительскими для данной. <br>
     * (Категории, у которых в подкатегориях есть переданная (явно или транзитивно))
     * @return список родительских категорий, упорядоченный по дальности от корневой директории
     */
    public List<Category> getParentCategories(Category category);

    /**
     * Проверяет является ли одна категория вложена в другую
     * @param parentCategory категория
     * @param childCategory подкатегория
     * @return {@code true}, если является, иначе false
     */
    public boolean isNestedCategory(Category parentCategory, Category childCategory);

    /**
     * Создает новую категорию на основе данной. Игнорирует и затирает поля {@link Category#getParentCategory()}
     * и {@link Category#getNested()}, так как это может нарушить консистентность данных.
     */
    public void createCategory(Category category);

    /**
     * Удаляет категорию и все ее вложенные категории
     */
    public void deleteCategory(Category category);
}
