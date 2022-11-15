package ru.practicum.explore.category.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDtoPatch {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
}
