package ru.practicum.explore.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.event.repository.EventRepositoryJpa;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.request.model.Status;
import ru.practicum.explore.validation.Validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepositoryJpa repository;
    private final Validation validation;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventShortDto> getAllPublicEvents(String text, Integer[] categories, Boolean paid,
                                                  String stringRangeStart, String stringRangeEnd,
                                                  Boolean onlyAvailable, String sort, int from, int size)
            throws IllegalArgumentEx {
        Pageable pageable = PageRequest.of(from / size, size);
        LocalDateTime rangeStart = stringRangeStart.isBlank() ? LocalDateTime.now() : LocalDateTime
                .parse(stringRangeStart, FORMATTER);
        LocalDateTime rangeEnd = stringRangeEnd.isBlank() ? LocalDateTime.MAX : LocalDateTime
                .parse(stringRangeEnd, FORMATTER);
        validation.validateDate(rangeStart, rangeEnd);
        List<Boolean> listPaid = paid == null ? List.of(true, false) : List.of(paid);
        List<Event> events;
        if (categories != null & onlyAvailable) {
            events = repository.getEventsAvailableInCat(text, listPaid, rangeStart, rangeEnd, Arrays.asList(categories),
                    Status.CONFIRMED, State.PUBLISHED);
        } else if (categories != null & !onlyAvailable) {
            events = repository.getEventsInCat(text, listPaid, rangeStart, rangeEnd, Arrays.asList(categories),
                    State.PUBLISHED);
        } else if (categories == null & onlyAvailable) {
            events = repository.getEventsAvailable(text, listPaid, rangeStart, rangeEnd,
                    Status.CONFIRMED, State.PUBLISHED);
        } else if (categories == null & !onlyAvailable) {
            events = repository.getEvents(text, listPaid, rangeStart, rangeEnd, State.PUBLISHED);
        }
        return null;
    }

}
