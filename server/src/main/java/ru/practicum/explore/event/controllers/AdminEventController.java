package ru.practicum.explore.event.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore.category.dto.CategoryDtoAnswer;
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
public class AdminEventController {
    private final EventService eventService;
    private static final String FIRST_ELEMENT = "0";
    private static final String PAGE_SIZE = "10";



}
