package ru.practicum.explore.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
}
