package ru.practicum.explore.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.event.repository.EventRepositoryJpa;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.request.dto.RequestDto;
import ru.practicum.explore.request.dto.RequestMapper;
import ru.practicum.explore.request.model.Request;
import ru.practicum.explore.request.model.Status;
import ru.practicum.explore.request.repository.RequestRepositoryJpa;
import ru.practicum.explore.user.dto.UserMapper;
import ru.practicum.explore.user.repository.UserRepositoryJpa;
import ru.practicum.explore.validation.Validation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepositoryJpa requestRepositoryJpa;
    private final EventRepositoryJpa eventRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final Validation validation;

    public List<RequestDto> getAllByUserRequests(Long userId) {
        validation.validateUser(userId);
        return requestRepositoryJpa.findAllByRequesterId(userId).stream()
                .map(p -> RequestMapper.toRequestDto(p))
                .collect(Collectors.toList());
    }

    public RequestDto addRequest(Long userId, Long eventId) {
        validation.validateEvent(eventId);
        validation.validateUser(userId);
        Event event = eventRepositoryJpa.findById(eventId).get();
        if (event.getState() != State.PUBLISHED) {
            throw new IllegalArgumentEx("Нельзя запрашивать участие в необуликованном событии");
        }
        if (event.getInitiator().getId() == userId) {
            throw new IllegalArgumentEx("Нельзя запрашивать участие в своем событии");
        }
        if (event.getConfirmRequests() == event.getParticipantLimit()) {
            throw new IllegalArgumentEx("Достигнут лимит запросов на участие");
        }
        if (!requestRepositoryJpa.getByRequesterByEvent(userId, eventId).isEmpty()) {
            throw new IllegalArgumentEx("нельзя добавить повторный запрос");
        }
        Request request = new Request();
        if (event.getRequestModeration()) {
            request.setStatus(Status.PENDING);
        } else {
            request.setStatus(Status.CONFIRMED);
            event.setConfirmRequests(event.getConfirmRequests() + 1);
            eventRepositoryJpa.save(event);
        }
        request.setRequester(userRepositoryJpa.findById(userId).get());
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        return RequestMapper.toRequestDto(requestRepositoryJpa.save(request));
    }

    public RequestDto cancelRequest(Long userId, Long requestId) {
        validation.validateUser(userId);
        validation.validateRequest(requestId);
        Request request = requestRepositoryJpa.findById(requestId).get();
        if (request.getRequester().getId() != userId) {
            throw new IllegalArgumentEx("Чужую заявку отменить нельзя");
        }
        if (request.getStatus().equals(Status.REJECTED) || request.getStatus().equals(Status.CANCELED)) {
            throw new IllegalArgumentEx("Заявка была отменена ранее");
        } else {
            Event event = eventRepositoryJpa.findById(request.getEvent().getId()).get();
            event.setConfirmRequests(event.getConfirmRequests() - 1);
            eventRepositoryJpa.save(event);
        }
        request.setStatus(Status.CANCELED);
        return RequestMapper.toRequestDto(requestRepositoryJpa.save(request));
    }
}