package ru.dmitry.naujava.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.CategoryRepository;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Сервис для работы с {@link Category}
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final PlatformTransactionManager transactionManager;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               PlatformTransactionManager transactionManager) {
        this.categoryRepository = categoryRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void nestCategory(Category parent, Category child) throws CategoryException {
        if(parent == null) {
            child.setParentCategory(null);
            categoryRepository.save(child);
        }

        if(child.equals(parent)) {
            throw new CategoryException("Cannot nest category into itself.");
        }

        if(getParentCategories(parent).contains(child)) { // если сейчас среди родителей parent есть child
            throw new CategoryException("Nesting categories would create a cycle.");
        }

        child.setParentCategory(parent);
        categoryRepository.save(child);
    }

    @Override
    public List<Category> getNestedCategories(Category category) {
        List<Category> result = new ArrayList<>();
        Queue<Category> queue = new ArrayDeque<>(categoryRepository.getNestedOf(category));
        while(!queue.isEmpty()) {
            Category nextCategory = queue.poll();
            result.add(nextCategory);
            queue.addAll(categoryRepository.getNestedOf(nextCategory));
        }
        return result;
    }

    @Override
    public List<Category> getParentCategories(Category category) {
        List<Category> parents = new ArrayList<>();
        Category parent = category.getParentCategory();
        while(parent != null) {
            parents.add(parent);
            parent = parent.getParentCategory();
        }
        return parents;
    }

    @Override
    public Category createCategory(String name, String description, Category parent, User user) throws CategoryException {
        Category category = new Category(name, description, parent, user);
        categoryRepository.save(category);
        return category;
    }

    @Transactional
    @Override
    public void deleteCategory(Category category) {
        List<Category> nestedCategories = getNestedCategories(category);
        for(int i = nestedCategories.size() - 1; i >= 0; i--) { // Удаляем с листьев
            categoryRepository.delete(nestedCategories.get(i));
        }
        categoryRepository.delete(category);
    }
}
