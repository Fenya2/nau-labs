package ru.dmitry.naujava.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.repository.CategoryRepository;
import ru.dmitry.naujava.service.CategoryService;

@Component
public class CommandProcessor {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CategoryFormatter categoryFormatter;

    @Autowired
    public CommandProcessor(CategoryService categoryService,
                            CategoryFormatter categoryFormatter,
                            CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryFormatter = categoryFormatter;
        this.categoryRepository = categoryRepository;
    }

    public void processCommand(String input) {
        String[] cmd = input.split(" ");
        switch(cmd[0]) {
            case "cr" -> processCreateCategory(cmd);
            case "inf" -> processInfoCategory(cmd);
            case "root" -> processRoot(cmd);
            case "nest" -> processNest(cmd);
            case "parentsof" ->processParentsOf(cmd);
            case "childsof" -> processChildsOf(cmd);
            case "del" -> processDelete(cmd);
            default -> System.out.println("Введена неизвестная команда...");
        }
    }

    /**
     * Обрабатывает команду удаления категории
     */
    private void processDelete(String[] cmd) {
        Category category = categoryRepository.read(Long.parseLong(cmd[1]));
        categoryService.deleteCategory(category);
        System.out.println("категория удалена!");
    }

    /**
     * Обрабатывает команду получения родительских категорий
     */
    private void processParentsOf(String[] cmd) {
        Category category = categoryRepository.read(Long.parseLong(cmd[1]));
        categoryService.getParentCategories(category).forEach(c -> System.out.println(categoryFormatter.format(c)));
    }

    /**
     * Обрабатывает команду получения вложенных категорий
     */
    private void processChildsOf(String[] cmd) {
        Category category = categoryRepository.read(Long.parseLong(cmd[1]));
        categoryService.getNestedCategories(category).forEach(c -> System.out.println(categoryFormatter.format(c)));
    }

    /**
     * Обрабатывает команду вложения одной категории в другую
     */
    private void processNest(String[] cmd) {
        Category parent = categoryRepository.read(Long.parseLong(cmd[1]));
        Category child = categoryRepository.read(Long.parseLong(cmd[2]));
        categoryService.nestCategory(parent, child);
        System.out.println("категория вложена!");
    }

    /**
     * Обрабатывает команду, выводящую корневые категории
     * (категории, которые никуда не вложены)
     */
    private void processRoot(String[] cmd) {
        categoryRepository.getAllRoots().forEach(category -> System.out.println(categoryFormatter.format(category)));
    }

    /**
     * Обрабатыват команду получения информации о категории
     */
    private void processInfoCategory(String[] cmd) {
        Category category = categoryRepository.read(Long.parseLong(cmd[1]));
        System.out.println("Информация о категории:");
        System.out.println("первый уровень вложенных категорий:");
        System.out.println(categoryFormatter.formatWithChildsFirstLevel(category));
        System.out.println("Вложена в:");
        System.out.println(categoryFormatter.format(category.getParentCategory()));
    }

    /**
     * Обрабатывает команду создания категории
     */
    private void processCreateCategory(String[] cmd) {
        Category category = new Category();
        category.setName(cmd[1]);
        category.setDescription(cmd[2]);
        categoryService.createCategory(category);
        System.out.println("Категория создана!");
        System.out.println(categoryFormatter.format(category));
    }
}
