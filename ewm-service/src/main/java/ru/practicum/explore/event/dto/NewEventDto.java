package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore.event.model.Location;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String annotation;
    @NotNull
    private Long category;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private Location location;
    @NotNull
    private Boolean paid;
    @NotNull
    private Integer participantLimit;
    @NotNull
    private Boolean requestModeration;
    @NotNull
    @NotEmpty
    private String title;
    private Long initiator;
    private String state;
    private String createdOn;
}
