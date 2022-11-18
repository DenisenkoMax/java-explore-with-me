package ru.practicum.explore.event.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.service.EventService;
import ru.practicum.explore.exception.IllegalArgumentEx;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/events",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PublicEventController {
    private final EventService eventService;
    private static final String FIRST_ELEMENT = "0";
    private static final String PAGE_SIZE = "10";

    @GetMapping("/events")
    public ResponseEntity<List<EventShortDto>> getAllPublicEvents(
            @RequestParam(name = "text", defaultValue = "") String text,
            @RequestParam(name = "categories", required = false) Integer[] categories,
            @RequestParam(name = "paid") Boolean paid,
            @RequestParam(name = "rangeStart", defaultValue = "") String rangeStart,
            @RequestParam(name = "rangeEnd", defaultValue = "") String rangeEnd,
            @RequestParam(name = "onlyAvailable") Boolean onlyAvailable,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "from", defaultValue = FIRST_ELEMENT) int from,
            @RequestParam(name = "size", defaultValue = PAGE_SIZE) int size) throws IllegalArgumentEx {
        if ((paid == null) || (onlyAvailable == null)) {
            new IllegalArgumentException("Unset paid or onlyAvailable variables");
        }
        return new ResponseEntity<>(eventService.getAllPublicEvents(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable,sort, from, size), HttpStatus.OK);
    }
}
