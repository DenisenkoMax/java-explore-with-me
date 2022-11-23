package ru.practicum.explore.event.service;

import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.dto.NewEventDto;
import ru.practicum.explore.event.dto.UpdateEventDto;
import ru.practicum.explore.event.model.SortState;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.request.dto.RequestDto;
import ru.practicum.explore.request.model.Request;

import java.util.List;

public interface EventService {
    List<EventShortDto> getAllEventsPublic(String text, Long[] categories, Boolean paid, String rangeStart,
                                           String rangeEnd, Boolean onlyAvailable, SortState sort, int from, int size)
            throws IllegalArgumentEx;

    EventFullDto getByIdPublic(Long eventId);

    List<EventShortDto> getAllEventsPrivate(Long userId, int from, int size);

    EventFullDto getEventByIdPrivate(Long userId, Long eventId);

    List<RequestDto> getEventRequestsPrivate(Long userId, Long eventId);

    RequestDto confirmRequestPrivate(Long userId, Long eventId, Long requestId);

    RequestDto rejectRequestPrivate(Long userId, Long eventId, Long requestId);

    EventFullDto updateEventPrivate(Long userId, UpdateEventDto updateEventDto) throws IllegalArgumentEx;

    EventFullDto addEventPrivate(Long userId, NewEventDto newEventDto) throws IllegalArgumentEx;

    EventFullDto cancelEventPrivate(Long userId, Long eventId);

    List<EventFullDto> getAllByAdmin(Long[] users, String[] states, Long[] categories, String rangeStart,
                                     String rangeEnd, int from, int size);

    EventFullDto updateEventByAdmin(Long eventId, NewEventDto newEventDto);

    EventFullDto publishEventAdmin(Long eventId);

    EventFullDto rejectEventAdmin(Long eventId);

}