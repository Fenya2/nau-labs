package ru.dmitry.naujava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.criteriaapi.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private User creator;

    @Autowired
    public CategoryRepositoryTest(
            CategoryRepository categoryRepository,
            UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        creator = new User("dsyromyatnikov", "fenya74.09@gmail.com", LocalDate.now());
        userRepository.save(creator);
    }

    @Test
    void testGetAllRoots() {
        Category rootCategory1 = new Category("1", "1", null, creator);
        Category rootCategory2 = new Category("2", "2", null, creator);
        Category childCategory = new Category("3", "3", rootCategory1, creator);

        categoryRepository.save(rootCategory1);
        categoryRepository.save(rootCategory2);
        categoryRepository.save(childCategory);

        List<Category> rootCategories = categoryRepository.getAllRoots();

        assertThat(rootCategories)
                .hasSize(2)
                .contains(rootCategory1, rootCategory2);
    }

    @Test
    void testGetNestedOf() {
        Category rootCategory = new Category("1", "1", null, creator);
        Category childCategory1 = new Category("2", "2", rootCategory, creator);
        Category childCategory2 = new Category("3", "3", rootCategory, creator);
        Category childCategory3 = new Category("4", "4", childCategory1, creator);

        categoryRepository.save(rootCategory);
        categoryRepository.save(childCategory1);
        categoryRepository.save(childCategory2);
        categoryRepository.save(childCategory3);

        List<Category> rootCategories = categoryRepository.getNestedOf(rootCategory);

        assertThat(rootCategories).containsExactly(childCategory1, childCategory2);
    }

    @Test
    void testGetCategoriesOfUser() {
        User creator2 = new User("impostor", "dim74.09@mail.ru", LocalDate.now());
        userRepository.save(creator2);

        Category category1 = new Category("1", "2", null, creator);
        Category category2 = new Category("2", "2", null, creator2);

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categories = categoryRepository.findByUser(creator);
        assertThat(categories).containsExactly(category1);
    }
}
