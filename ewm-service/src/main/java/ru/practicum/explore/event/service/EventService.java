package ru.practicum.explore.event.service;

import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.exception.IllegalArgumentEx;

import java.util.List;

public interface EventService {
    List<EventShortDto> getAllPublicEvents(String text, Integer[] categories, Boolean paid, String rangeStart,
                                           String rangeEnd, Boolean onlyAvailable, String sort, int from, int size)
            throws IllegalArgumentEx;
}