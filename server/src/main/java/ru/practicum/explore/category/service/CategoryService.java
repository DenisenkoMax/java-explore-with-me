package ru.practicum.explore.category.service;

import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.dto.CategoryDtoAnswer;
import ru.practicum.explore.category.dto.CategoryDtoPatch;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<CategoryDtoAnswer> createCategory(CategoryDto categoryDto);
    Optional<CategoryDtoAnswer> updateCategory(CategoryDtoPatch categoryDtoPatch) throws NotFoundEx;
    void deleteCategoryById(long id) throws NotFoundEx;
    List<CategoryDtoAnswer> getAllCategories(int from, int size) throws IllegalArgumentEx;
    Optional<CategoryDtoAnswer> getCategoryById(long id) throws NotFoundEx;

}
