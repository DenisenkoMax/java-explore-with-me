package ru.practicum.explore.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.service.CategoryService;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/categories",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PublicCategoryController {
    private final CategoryService categoryService;
    private static final String FIRST_ELEMENT = "0";
    private static final String PAGE_SIZE = "10";


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory(
            @RequestParam(name = "from", defaultValue = FIRST_ELEMENT) int from,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE) int size) throws IllegalArgumentEx {
        log.info("Запрошены все категории");
        return new ResponseEntity<>(categoryService.getAllCategories(from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto>  getCategoryById(@PathVariable long id) throws NotFoundEx {
        log.info("Запрошена категория id: {}", id);
        return categoryService.getCategoryById(id).map(itemResult -> new ResponseEntity<>(itemResult,
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
