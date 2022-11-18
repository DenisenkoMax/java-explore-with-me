package ru.practicum.explore.event.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/events",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AdminEventController {
   // private final EventService eventService;
    private static final String FIRST_ELEMENT = "0";
    private static final String PAGE_SIZE = "10";



}
