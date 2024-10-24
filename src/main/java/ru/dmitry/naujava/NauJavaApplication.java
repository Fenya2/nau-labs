package ru.dmitry.naujava;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.dmitry.naujava.entity.Category;
import ru.dmitry.naujava.repository.CategoryRepository;
import ru.dmitry.naujava.service.CategoryService;

@SpringBootApplication
public class NauJavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NauJavaApplication.class, args);
    }
}
