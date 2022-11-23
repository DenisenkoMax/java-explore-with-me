package ru.practicum.explore.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.client.BaseClient;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.model.SortState;
import ru.practicum.explore.event.service.EventService;
import ru.practicum.explore.exception.IllegalArgumentEx;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/events",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PublicEventController {
    private final EventService eventService;
    private final BaseClient baseClient;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllPublicEvents(
            @RequestParam(name = "text", defaultValue = "") String text,
            @RequestParam(name = "categories", required = false) Long[] categories,
            @RequestParam(name = "paid") Boolean paid,
            @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
            @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
            @RequestParam(name = "onlyAvailable") Boolean onlyAvailable,
            @RequestParam(name = "sort") String sort,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
            @Positive @RequestParam(value = "size", defaultValue = "10") int size,
            HttpServletRequest request) throws IllegalArgumentEx {
        SortState sortState = SortState.from(sort)
                .orElseThrow(() -> new IllegalArgumentException("Неизвестный тип сортировки {} " + sort));
        baseClient.addHit(request);
        log.info("Запрошен список событий");
        return new ResponseEntity<>(eventService.getAllEventsPublic(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable,sortState, from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventsById(@PathVariable(name = "id") Long eventId, HttpServletRequest request) {
        baseClient.addHit(request);
        log.info("Запрошено событие id: {}", eventId);
        return eventService.getByIdPublic(eventId);
    }
}
