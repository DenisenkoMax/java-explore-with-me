package ru.practicum.explore.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.repository.CategoryRepositoryJpa;
import ru.practicum.explore.client.BaseClient;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.event.repository.EventRepositoryJpa;
import ru.practicum.explore.exception.NotFoundEx;
import ru.practicum.explore.request.dto.RequestDto;
import ru.practicum.explore.request.dto.RequestMapper;
import ru.practicum.explore.request.model.Request;
import ru.practicum.explore.request.model.Status;
import ru.practicum.explore.request.repository.RequestRepositoryJpa;
import ru.practicum.explore.user.repository.UserRepositoryJpa;
import ru.practicum.explore.validation.Validation;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepositoryJpa eventRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final CategoryRepositoryJpa categoryRepositoryJpa;
    private final RequestRepositoryJpa requestRepositoryJpa;
    private final Validation validation;
    private final BaseClient baseClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<EventShortDto> getAllEventsPrivate(Long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepositoryJpa.findAllByInitiator(userId, pageable);
        return events.stream().map(p -> EventMapper.toEventShortDto(p)).collect(Collectors.toList());
    }

    public EventFullDto updateEventPrivate(Long userId, UpdateEventDto dto) {
        validation.validateEventDate(dto.getEventDate());
        validation.validateUser(userId);
        validation.validateEvent(dto.getEventId());
        validation.validateEventOwnerState(userId, dto.getEventId());
        validation.validateEventStatePendingOrCanceled(dto.getEventId());
        Event event = eventRepositoryJpa.findById(dto.getEventId()).get();
        if (dto.getAnnotation() != null) event.setAnnotation(dto.getAnnotation());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) event.setEventDate(LocalDateTime.parse(dto.getEventDate(), FORMATTER));
        if (dto.getPaid() != null) event.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());
        if (dto.getCategory() != null) event.setCategory(categoryRepositoryJpa.findById(dto.getCategory()).get());
        event.setState(State.PENDING);
        eventRepositoryJpa.save(event);
        return EventMapper.toEventFullDto(event);
    }

    public EventFullDto addEventPrivate(Long userId, NewEventDto newEventDto) {
        validation.validateEventDate(newEventDto.getEventDate());
        validation.validateUser(userId);
        Event event = EventMapper.toEvent(newEventDto);
        event.setInitiator(userRepositoryJpa.findById(userId).get());
        event.setCategory(categoryRepositoryJpa.findById(newEventDto.getCategory()).get());
        event.setInitiator(userRepositoryJpa.findById(userId).get());
        event.setCategory(categoryRepositoryJpa.findById(newEventDto.getCategory()).get());
        return EventMapper.toEventFullDto(eventRepositoryJpa.save(event));
    }

    public EventFullDto getEventByIdPrivate(Long userId, Long eventId) {
        Optional<Event> event = Optional.of(eventRepositoryJpa.findByIdAndInitiator(userId, eventId));
        if (!event.isPresent()) {
            throw new NotFoundEx("Событие не найдено", eventId);
        }
        return EventMapper.toEventFullDto(event.get());
    }

    public EventFullDto cancelEventPrivate(Long userId, Long eventId) {
        validation.validateUser(userId);
        validation.validateEvent(eventId);
        validation.validateEventOwnerState(userId, eventId);
        validation.validateEventStatePending(eventId);
        Event event = eventRepositoryJpa.findById(eventId).get();
        event.setState(State.CANCELED);
        eventRepositoryJpa.save(event);
        return EventMapper.toEventFullDto(event);
    }

    public List<RequestDto> getEventRequestsPrivate(Long userId, Long eventId) {
        validation.validateUser(userId);
        validation.validateEvent(eventId);
        //  return requestRepositoryJpa.getEventRequestsPrivate(userId, eventId);
        return requestRepositoryJpa.getEventRequestsPrivate(userId, eventId)
                .stream().map(p -> RequestMapper.toRequestDto(p)).collect(Collectors.toList());
    }

    public RequestDto confirmRequestPrivate(Long userId, Long eventId, Long requestId) {
        validation.validateUser(userId);
        validation.validateEvent(eventId);
        validation.validateRequest(requestId);
        validation.validateEventOwner(userId, eventId);
        Request request = requestRepositoryJpa.findById(requestId).get();
        Event event = eventRepositoryJpa.findById(eventId).get();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            return RequestMapper.toRequestDto(request);
        }
        if (request.getStatus() == Status.CONFIRMED) {
            throw new IllegalArgumentException("Заявка уже была подтверждена ранее");
        }
        if (event.getConfirmRequests() == event.getParticipantLimit()) {
            throw new IllegalArgumentException("достигнут лимит запросов на участие");
        }
        request.setStatus(Status.CONFIRMED);
        event.setConfirmRequests(event.getConfirmRequests() + 1);
        eventRepositoryJpa.save(event);
        return RequestMapper.toRequestDto(request);
    }

    public RequestDto rejectRequestPrivate(Long userId, Long eventId, Long requestId) {
        validation.validateUser(userId);
        validation.validateEvent(eventId);
        validation.validateRequest(requestId);
        validation.validateEventOwner(userId, eventId);
        Request request = requestRepositoryJpa.findById(requestId).get();
        if (request.getStatus().equals(Status.REJECTED) || request.getStatus().equals(Status.CANCELED)) {
            throw new IllegalArgumentException("Заявка была отменена ранее");
        }
        request.setStatus(Status.REJECTED);
        return RequestMapper.toRequestDto(requestRepositoryJpa.save(request));
    }

    @Override
    public List<EventShortDto> getAllEventsPublic(String text, Long[] categories, Boolean paid,
                                                  String strStart, String strEnd, Boolean onlyAvailable,
                                                  String strSort, int from, int size, HttpServletRequest request) {
        Pageable pageable = (strSort != null) ?
                PageRequest.of(from / size, size, getSort(strSort)) :
                PageRequest.of(from / size, size);
        List<Event> events = new ArrayList<>();
        if (categories == null) categories = categoryRepositoryJpa.findAllId().stream()
                .toArray(Long[]::new);
        LocalDateTime start = ((strStart == null) || (strStart.isBlank())) ? LocalDateTime.now()
                : LocalDateTime.parse(strStart, FORMATTER);
        LocalDateTime end = ((strEnd == null) || (strStart.isBlank())) ? LocalDateTime.now().plusYears(1000)
                : LocalDateTime.parse(strEnd, FORMATTER);
        validation.validateDate(start, end);
        List<Boolean> listPaid = (paid == null) ? List.of(true, false) : List.of(paid);

        if (onlyAvailable) {
            events = eventRepositoryJpa.getEventsOnlyAvailable(text, listPaid, start, end, Arrays.asList(categories),
                    State.PUBLISHED, pageable);
        } else if (!onlyAvailable) {
            events = eventRepositoryJpa.getEventsAll(text, listPaid, start, end, Arrays.asList(categories),
                    State.PUBLISHED, pageable);
        }
        addViews(events);
        baseClient.addHit(request);
        return events.stream()
                .map(p -> EventMapper.toEventShortDto(p))
                .collect(Collectors.toList());
    }

    public EventFullDto getByIdPublic(Long eventId, HttpServletRequest request) {
        Event event = eventRepositoryJpa.getEventByIdByState(eventId, State.PUBLISHED);
        if (event == null) {
            throw new NotFoundEx("Событие не найдено", eventId);
        }
        addViews(List.of(event));
        baseClient.addHit(request);
        return EventMapper.toEventFullDto(event);
    }

    private Sort getSort(String strSort) {
        if (strSort == null) {
            throw new IllegalArgumentException("Не указан тип сортировки");
        } else {
            switch (strSort) {
                case "EVENT_DATE":
                    return Sort.by(Sort.Direction.DESC, "eventDate");

                case "VIEWS":
                    return Sort.by(Sort.Direction.DESC, "views");
                default:
                    throw new IllegalArgumentException("Неизвестный тип сортировки {} " + strSort);
            }
        }
    }

    private void addViews(List<Event> events) {
        String[] uris = new String[events.size()];
        for (int i = 0; i < events.size(); i++) {
            uris[i] = ("/events/" + events.get(i).getId());
        }
        HashMap<String, Integer> views = baseClient.getStats(new Timestamp(0).toLocalDateTime(), LocalDateTime.now(),
                null, false);
        System.out.println(views);
        for (Event event : events) {
            String key = ("/events/" + event.getId());
            if (views.get(key) != null) {
                event.setViews(views.get(key));
            }
        }
    }

    public List<EventFullDto> getAllByAdmin(Long[] users, String[] states, Long[] categories, String strStart,
                                            String strEnd, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = new ArrayList<>();
        List<State> listStates = new ArrayList<>();
        if (states == null) {
            listStates = List.of(State.PUBLISHED, State.CANCELED, State.PENDING);
        } else {
            for (String strState : states) {
                State state = State.from(strState)
                        .orElseThrow(() -> new IllegalArgumentException("Неизместный статус {}" + strState));
                listStates.add(state);
            }
        }
        if (categories == null) categories = categoryRepositoryJpa.findAllId().stream()
                .toArray(Long[]::new);
        LocalDateTime start = ((strStart == null) || (strStart.isBlank())) ? LocalDateTime.now()
                : LocalDateTime.parse(strStart, FORMATTER);
        LocalDateTime end = ((strEnd == null) || (strStart.isBlank())) ? LocalDateTime.now().plusYears(1000)
                : LocalDateTime.parse(strEnd, FORMATTER);
        validation.validateDate(start, end);
        if (users == null) {
            events = eventRepositoryJpa.getAllAdmin(listStates, Arrays.asList(categories), start, end, pageable);
        } else {
            events = eventRepositoryJpa.getAllByUsersAdmin(listStates, Arrays.asList(categories), start, end,
                    Arrays.asList(users), pageable);
        }
        return events.stream()
                .map(p -> EventMapper.toEventFullDto(p))
                .collect(Collectors.toList());
    }

    public EventFullDto updateEventByAdmin(Long eventId, NewEventDto dto) {
        validation.validateEvent(eventId);
        Event event = eventRepositoryJpa.findById(eventId).get();
        if (dto.getAnnotation() != null) event.setAnnotation(dto.getAnnotation());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) event.setEventDate(LocalDateTime.parse(dto.getEventDate(), FORMATTER));
        if (dto.getPaid() != null) event.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());
        if (dto.getCategory() != null) event.setCategory(categoryRepositoryJpa.findById(dto.getCategory()).get());
        if (dto.getLocation() != null) event.setLocation(dto.getLocation());
        if (dto.getRequestModeration() != null) event.setRequestModeration(dto.getRequestModeration());
        eventRepositoryJpa.save(event);
        return EventMapper.toEventFullDto(event);
    }

    public EventFullDto publishEventAdmin(Long eventId) {
        validation.validateEvent(eventId);
        Event event = eventRepositoryJpa.findById(eventId).get();
        if (LocalDateTime.now().plusHours(1).isAfter(event.getEventDate())) {
            throw new IllegalArgumentException("дата начала события должна быть не ранее чем за час от даты публикации");
        }
        if (event.getState() != State.PENDING) {
            throw new IllegalArgumentException("событие должно быть в состоянии ожидания публикации");
        }
        event.setState(State.PUBLISHED);
        return EventMapper.toEventFullDto(eventRepositoryJpa.save(event));
    }

    public EventFullDto rejectEventAdmin(Long eventId) {
        validation.validateEvent(eventId);
        Event event = eventRepositoryJpa.findById(eventId).get();
        if (event.getState() != State.PENDING) {
            throw new IllegalArgumentException("событие должно быть в состоянии ожидания публикации");
        }
        event.setState(State.CANCELED);
        return EventMapper.toEventFullDto(eventRepositoryJpa.save(event));
    }
}
