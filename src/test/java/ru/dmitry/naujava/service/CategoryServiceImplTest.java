package ru.dmitry.naujava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.CategoryRepository;

import java.util.List;

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

        List<Category> nestedCategories = categoryService.getNestedCategories(parent);

        assertThat(nestedCategories).containsExactly(child1, child2, grandChild);
    }

    @Test
    void testNestCategorySuccess() {
        Category parent = new Category();
        parent.setId(1);
        Category child = new Category();
        child.setId(2);
        categoryService.nestCategory(parent, child);
        assertThat(parent).isEqualTo(child.getParentCategory());
        verify(categoryRepository, times(1)).save(child);
    }

    @Test
    void testNestCategoryFailureNestToSelf() {
        Category category = new Category();
        category.setId(1);
        CategoryException exception = Assertions.assertThrows(
                CategoryException.class, () -> categoryService.nestCategory(category, category));
        assertThat(exception.getMessage()).isEqualTo("Cannot nest category into itself.");
    }

    @Test
    void testNestCategoryFailureCycle() {
        Category parent = new Category();
        parent.setId(1);
        Category child = new Category();
        child.setId(2);

        parent.setParentCategory(child);
        CategoryException exception = Assertions.assertThrows(
                CategoryException.class, () -> categoryService.nestCategory(parent, child));
        assertThat(exception.getMessage()).isEqualTo("Nesting categories would create a cycle.");
    }

    @Test
    void getParentCategories() {
        Category grand = new Category();
        grand.setId(1);
        Category dad = new Category();
        dad.setId(2);
        Category son = new Category();
        son.setId(3);

        son.setParentCategory(dad);
        dad.setParentCategory(grand);

        assertThat(categoryService.getParentCategories(son)).containsExactly(dad, grand);
    }

    @Test
    void testCreateCategory() {
        User user = new User();
        user.setId(1);
        Category parent = new Category();
        parent.setId(1);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0, Category.class);
            category.setId(2);
            return null;
        });

        Category category = categoryService.createCategory("name", "description", parent, user);
        assertThat(category.getId()).isEqualTo(2);
        assertThat(category.getParentCategory()).isEqualTo(parent);
        assertThat(category.getName()).isEqualTo("name");
        assertThat(category.getDescription()).isEqualTo("description");
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testDeleteCategory() {
        Category grand = new Category();
        grand.setId(1);
        Category dad = new Category();
        dad.setId(2);
        Category son = new Category();
        son.setId(3);

        son.setParentCategory(dad);
        dad.setParentCategory(grand);

        when(categoryRepository.getNestedOf(grand)).thenReturn(List.of(dad));
        when(categoryRepository.getNestedOf(dad)).thenReturn(List.of(son));
        when(categoryRepository.getNestedOf(son)).thenReturn(List.of());

        categoryService.deleteCategory(grand);

        InOrder inOrder = inOrder(categoryRepository);
        inOrder.verify(categoryRepository).delete(son);
        inOrder.verify(categoryRepository).delete(dad);
        inOrder.verify(categoryRepository).delete(grand);
    }
}
