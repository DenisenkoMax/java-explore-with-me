package ru.practicum.explore.category.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
}
