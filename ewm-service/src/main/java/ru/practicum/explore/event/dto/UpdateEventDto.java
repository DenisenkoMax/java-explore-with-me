package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventDto {
    private Long eventId;
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
