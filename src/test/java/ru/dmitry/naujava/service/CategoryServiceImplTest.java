package ru.dmitry.naujava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.repository.CategoryRepository;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNestedCategories() {
        Category parent = new Category();
        parent.setId(1);
        Category child1 = new Category();
        child1.setId(2);
        Category child2 = new Category();
        child2.setId(3);
        Category grandChild = new Category();
        grandChild.setId(4);

        when(categoryRepository.getNestedOf(parent)).thenReturn(List.of(child1, child2));
        when(categoryRepository.getNestedOf(child1)).thenReturn(List.of(grandChild));
        when(categoryRepository.getNestedOf(child2)).thenReturn(List.of());
        when(categoryRepository.getNestedOf(grandChild)).thenReturn(List.of());

        Set<Category> nestedCategories = categoryService.getNestedCategories(parent);

        assertThat(nestedCategories).containsExactly(child1, child2, grandChild);
    }
}
