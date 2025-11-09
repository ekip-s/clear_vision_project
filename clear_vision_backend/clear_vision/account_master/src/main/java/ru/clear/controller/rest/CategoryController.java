package ru.clear.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clear.model.operation.operation_category.OperationCategory;
import ru.clear.service.category.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category/api/v1")
@Tag(name="category_controller", description = "Методы для управления категориями операций")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Получить категории",
            description = "Возвращает все категории"
    )
    @GetMapping
    public List<OperationCategory> getCategory() {
        log.info("GET: category_controller getCategory");
        return categoryService.getCategory();
    }
}
