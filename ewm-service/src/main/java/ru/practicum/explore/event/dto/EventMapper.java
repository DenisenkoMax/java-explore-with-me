package ru.practicum.explore.event.dto;

import ru.practicum.explore.category.dto.CategoryMapper;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.user.dto.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventFullDto toEventFullDto(Event event) {
        if (event == null) return null;
        else
            return new EventFullDto(
                    event.getId(),
                    event.getAnnotation(),
                    CategoryMapper.toCategoryDto(event.getCategory()),
                    event.getConfirmRequests(),
                    event.getCreatedOn(),
                    event.getDescription(),
                    event.getEventDate(),
                    UserMapper.toUserShortDto(event.getInitiator()),
                    event.getLocation(),
                    event.getPaid(),
                    event.getParticipantLimit(),
                    event.getPublishetOn(),
                    event.getRequestModeration(),
                    event.getState().toString(),
                    event.getTitle(),
                    event.getViews()
            );
    }

    public static EventShortDto toEventShortDto(Event event) {
        if (event == null) return null;
        else
            return new EventShortDto(
                    event.getId(),
                    event.getAnnotation(),
                    CategoryMapper.toCategoryDto(event.getCategory()),
                    0,
                    event.getEventDate(),
                    UserMapper.toUserShortDto(event.getInitiator()),
                    event.getPaid(),
                    event.getTitle(),
                    0
            );
    }

    public static NewEventDto toNewEventDto(Event event) {
        if (event == null) return null;
        else
            return new NewEventDto(
                    event.getId(),
                    event.getAnnotation(),
                    event.getCategory().getId(),
                    event.getDescription(),
                    event.getEventDate().format(FORMATTER),
                    event.getLocation(),
                    event.getPaid(),
                    event.getParticipantLimit(),
                    event.getRequestModeration(),
                    event.getTitle(),
                    event.getInitiator().getId(),
                    event.getState().toString(),
                    event.getCreatedOn().format(FORMATTER)
            );
    }

    public static Event toEvent(NewEventDto newEventDto) {
        if (newEventDto == null) return null;
        else
            return new Event(
                    0L,
                    newEventDto.getAnnotation(),
                    newEventDto.getTitle(),
                    newEventDto.getPaid(),
                    null,
                    LocalDateTime.parse(newEventDto.getEventDate(), FORMATTER),
                    LocalDateTime.now(),
                    newEventDto.getDescription(),
                    newEventDto.getParticipantLimit(),
                    newEventDto.getRequestModeration(),
                    State.PENDING,
                    newEventDto.getLocation(),
                    null,
                    null,
                    null,
                    0,
                    0
            );
    }
}
