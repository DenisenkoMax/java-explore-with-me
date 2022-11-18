package ru.practicum.explore.category.service;

import ru.practicum.explore.category.dto.NewCategoryDto;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<CategoryDto> createCategory(NewCategoryDto newCategoryDto);
    Optional<CategoryDto> updateCategory(CategoryDto categoryDto) throws NotFoundEx;
    void deleteCategoryById(long id) throws NotFoundEx;
    List<CategoryDto> getAllCategories(int from, int size) throws IllegalArgumentEx;
    Optional<CategoryDto> getCategoryById(long id) throws NotFoundEx;

}
