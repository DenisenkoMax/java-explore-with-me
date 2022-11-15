package ru.practicum.explore.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.dto.CategoryMapper;
import ru.practicum.explore.event.dto.EventDtoPublicAnswer;
import ru.practicum.explore.event.repository.EventRepositoryJpa;
import ru.practicum.explore.exception.IllegalArgumentEx;
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

    /*   @Override
    public List<EventDtoPublicAnswer> getAllPublicEvents(String text, Integer[] categories, Boolean paid,
                                                         String stringRangeStart, String stringRangeEnd,
                                                         Boolean onlyAvailable, int from, int size)
            throws IllegalArgumentEx {
        validation.validatePagination(from, size);
        Pageable pageable = PageRequest.of(from / size, size);
        LocalDateTime rangeStart =stringRangeStart.isBlank()?LocalDateTime.now():LocalDateTime
                .parse(stringRangeStart, FORMATTER);
        LocalDateTime rangeEnd =stringRangeEnd.isBlank()?LocalDateTime.MAX:LocalDateTime
                .parse(stringRangeEnd, FORMATTER);
        validation.validateDate(rangeStart,rangeEnd);

        return repository.getAllPublicEventsWithText(text, Arrays.asList(categories), paid, rangeStart, rangeEnd, onlyAvailable, pageable);
                .stream().
                map(p -> CategoryMapper.toCategoryDtoAnswer(p)).collect(Collectors.toList());
    }

     */
}
