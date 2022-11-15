package ru.practicum.explore.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore.category.dto.CategoryDtoAnswer;
import ru.practicum.explore.user.dto.UserDtoAnswer;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDtoPublicAnswer {
    private Long id;
    private String annotation;
    private CategoryDtoAnswer categoryDtoAnswer;
    private int confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserDtoAnswer initiator;
    private Boolean paid;
    private String title;
    private int views;
}
