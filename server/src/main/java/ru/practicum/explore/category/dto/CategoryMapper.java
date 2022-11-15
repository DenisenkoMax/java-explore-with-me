package ru.practicum.explore.category.dto;

import ru.practicum.explore.category.model.Category;

public class CategoryMapper {
    public static CategoryDtoAnswer toCategoryDtoAnswer(Category category) {
        if (category == null) return null;
        else return new CategoryDtoAnswer(
                category.getId(),
                category.getName()
        );
    }


    public static Category toCategory(CategoryDto categoryDto) {
        if (categoryDto == null) return null;
        else return new Category(
                0L,
                categoryDto.getName()
        );
    }

    public static Category toCategory(CategoryDtoPatch categoryDtoPatch) {
        if (categoryDtoPatch == null) return null;
        else return new Category(
                categoryDtoPatch.getId(),
                categoryDtoPatch.getName()
        );
    }
}
