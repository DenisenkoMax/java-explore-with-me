package ru.practicum.explore.event.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EventDtoAnswer {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
}
