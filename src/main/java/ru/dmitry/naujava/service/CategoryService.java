package ru.dmitry.naujava.service;

import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.entity.User;

import java.util.List;

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
     * Возвращает список категорий, вложенных в данную.
     * Список упорядочен по уровням вложенности категорий в данную.
     * Т.Е в начале списка категории непосредственно вложенные в переданную категорию,
     * в конце - категории, вложенные в данную и не имеющие вложенных категорий (листья)
     */
    public List<Category> getNestedCategories(Category category);

    /**
     * Возвращает список категорий, являющихся родительскими для данной. <br>
     * (Категории, у которых в подкатегориях есть переданная (явно или транзитивно))
     *
     * @return список родительских категорий, упорядоченный по дальности от корневой директории
     */
    public List<Category> getParentCategories(Category category);

    /**
     * Создает категорию, проверяя целостность данных перед добавлением в систему
     * @param name имя категории
     * @param description описание категории
     * @param parent категория, куда создаваемая вложена
     * @param user создатель категории
     * @return созданная категория
     * @throws CategoryException если создать категорию с переданными данными не получилось
     */
    public Category createCategory(String name, String description, Category parent, User user) throws CategoryException;

    /**
     * Удаляет категорию и все ее вложенные категории
     */
    public void deleteCategory(Category category);
}
