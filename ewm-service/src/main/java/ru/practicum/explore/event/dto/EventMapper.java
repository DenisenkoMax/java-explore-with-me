package ru.practicum.explore.event.dto;
import ru.practicum.explore.category.dto.CategoryMapper;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.user.dto.UserMapper;

public class EventMapper {
    public static EventFullDto toEventFullDto(Event event) {
        if (event == null) return null;
        else
            return new EventFullDto(
                    event.getId(),
                    event.getAnnotation(),
                    CategoryMapper.toCategoryDto(event.getCategory()),
                    0,
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
                    0
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
}
