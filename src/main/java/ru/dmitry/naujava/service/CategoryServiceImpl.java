package ru.dmitry.naujava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.repository.CategoryRepository;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Сервис для работы с {@link Category категориями}
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    /**
     * DAO для работы с категориями
     */
    private final CategoryRepository categoryRepository;

    /**
     * Менеджер транзакций
     */
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, PlatformTransactionManager transactionManager) {
        this.categoryRepository = categoryRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void nestCategory(Category parent, Category child) throws CategoryException {
        if(parent == null) {
            removeChildFromParentNested(child);
            child.setParentCategory(null);
            categoryRepository.save(child);
            return;
        }

        if(parent.equals(child)) {
            throw new CategoryException("can't nest a %s within itself."
                    .formatted(parent.asLogString()));
        }

        if(getParentCategories(parent).contains(child)) {
            throw new CategoryException("can't nest parent %s in child %s."
                    .formatted(parent.asLogString(), child.asLogString()));
        }

        removeChildFromParentNested(child);
        child.setParentCategory(parent);

        categoryRepository.save(parent);
        categoryRepository.save(child);
    }

    @Override
    public Set<Category> getNestedCategories(Category category) {
        Set<Category> nestedCategories = new HashSet<>();
        Queue<Category> categoryQueue = new ArrayDeque<>(categoryRepository.getNestedOf(category));
        while(!categoryQueue.isEmpty()) {
            Category c = categoryQueue.poll();
            nestedCategories.add(c);
            categoryQueue.addAll(categoryRepository.getNestedOf(c));
        }
        return nestedCategories;
    }

    @Override
    public List<Category> getParentCategories(Category category) {
        List<Category> parentCategories = new ArrayList<>();
        Category parent = category.getParentCategory();
        while(parent != null) {
            parentCategories.add(parent);
            parent = parent.getParentCategory();
        }
        return parentCategories;
    }

    @Override
    public void createCategory(Category category) {
        category.setParentCategory(null);
//        category.setNested(null);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
//        getNestedCategories(category).forEach(c -> categoryRepository.deleteById(c.getId()));
        removeChildFromParentNested(category);
        categoryRepository.deleteById(category.getId());
    }

    /**
     * Удаляет категорию из категории, в которую переданная вложена.
     * Если переданная категория никуда не вложена, ничего не делает.
     */
    private void removeChildFromParentNested(Category category) {
        Category parent = category.getParentCategory();
        if(parent != null) {
//            parent.getNested().remove(category);
            categoryRepository.save(parent);
        }
    }
}
