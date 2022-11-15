package ru.practicum.explore.category.dto;

import lombok.Data;

@Data
public class CategoryDtoAnswer {
    private Long id;
    private String name;

    public CategoryDtoAnswer(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
