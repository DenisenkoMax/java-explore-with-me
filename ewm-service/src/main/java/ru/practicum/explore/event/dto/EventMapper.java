package ru.practicum.explore.event.dto;

import ru.practicum.explore.category.dto.CategoryDtoAnswer;
import ru.practicum.explore.category.dto.CategoryMapper;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.user.dto.UserMapper;

public class EventMapper {
    public static EventDtoAdminAnswer toEventDtoAdminAnswer(Event event) {
        if (event == null) return null;
        else
            return new EventDtoAdminAnswer(
                    event.getId(),
                    event.getAnnotation(),
                    CategoryMapper.toCategoryDtoAnswer(event.getCategory()),
                    0,
                    event.getCreatedOn(),
                    event.getDescription(),
                    event.getEventDate(),
                    UserMapper.toUserDtoAnswer(event.getInitiator()),
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

    public static EventDtoPublicAnswer toEventDtoPublicAnswer(Event event) {
        if (event == null) return null;
        else
            return new EventDtoPublicAnswer(
                    event.getId(),
                    event.getAnnotation(),
                    CategoryMapper.toCategoryDtoAnswer(event.getCategory()),
                    0,
                    event.getEventDate(),
                    UserMapper.toUserDtoAnswer(event.getInitiator()),
                    event.getPaid(),
                    event.getTitle(),
                    0
            );
    }
}
