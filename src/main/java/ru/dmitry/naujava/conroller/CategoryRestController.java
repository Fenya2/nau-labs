package ru.dmitry.naujava.conroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitry.naujava.Constants;
import ru.dmitry.naujava.dto.CategoryDTO;
import ru.dmitry.naujava.service.CategoryService;


@RestController
@Tag(name = "Category rest controller", description = "Контроллер для работы с категориями")
@RequestMapping(Constants.BASE_REST_ENDPOINT + "/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    @Operation(
            summary = "Создание категории",
            description = "Создает категорию на основе переданного DTO"
    )
    public ResponseEntity<Void> createCategory(
            @Parameter(description = "DTO категории") @RequestBody CategoryDTO category
    ) {
        categoryService.createCategory(category);
        return ResponseEntity.ok(null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Exception exception(Exception e) {
        Exception exception = new Exception(e.getMessage());
        exception.setStackTrace(new StackTraceElement[] {});
        return exception;
    }
}
