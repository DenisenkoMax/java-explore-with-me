package ru.practicum.explore.category.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.category.dto.NewCategoryDto;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.service.CategoryService;
import ru.practicum.explore.exception.NotFoundEx;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/admin/categories",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto).map(newUser -> new ResponseEntity<>(newUser, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable long id) throws NotFoundEx {
        categoryService.deleteCategoryById(id);
    }

    @PatchMapping
    public ResponseEntity<CategoryDto> updateItem(@Valid @RequestBody CategoryDto categoryDto)
            throws NotFoundEx {


        return categoryService.updateCategory(categoryDto).map(itemResult -> new ResponseEntity<>(itemResult,
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
