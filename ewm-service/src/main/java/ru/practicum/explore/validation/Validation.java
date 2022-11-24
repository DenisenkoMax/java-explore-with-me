package ru.practicum.explore.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.repository.CategoryRepositoryJpa;
import ru.practicum.explore.compilation.repository.CompilationRepositoryJpa;
import ru.practicum.explore.event.dto.NewEventDto;
import ru.practicum.explore.event.model.Event;
import ru.practicum.explore.event.model.State;
import ru.practicum.explore.event.repository.EventRepositoryJpa;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;
import ru.practicum.explore.request.repository.RequestRepositoryJpa;
import ru.practicum.explore.user.repository.UserRepositoryJpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class Validation {
    private final UserRepositoryJpa userRepository;
    private final EventRepositoryJpa eventRepositoryJpa;
    private final CategoryRepositoryJpa categoryRepository;
    private final RequestRepositoryJpa requestRepositoryJpa;
    private final CompilationRepositoryJpa compilationRepositoryJpa;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 /*   private final ItemRepositoryJpa itemRepository;
    private final BookingRepositoryJpa bookingRepository;
  */

    public void validateDate(LocalDateTime start, LocalDateTime end) throws IllegalArgumentEx {
        if (end.isBefore(start)) {
            throw new IllegalArgumentEx("Дата завершения раньше чем дата старта");
        }
    }

    public void validateEventDate(String eventDate) throws IllegalArgumentEx {
        if (LocalDateTime.parse(eventDate, FORMATTER).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalArgumentEx("Обратите внимание: дата и время на которые намечено событие не может" +
                    " быть раньше, чем через два часа от текущего момента");
        }
    }

    public void validateEventOwnerState(Long userId, Long eventId) throws IllegalArgumentEx {

        if (userId != eventRepositoryJpa.findById(eventId).get().getInitiator().getId()) {
            throw new IllegalArgumentEx("Попытка именения события добавленного не текущим пользователем");
        }
    }


    public void validateEventStatePendingOrCanceled(Long eventId) throws IllegalArgumentEx {
        Event event = eventRepositoryJpa.findById(eventId).get();
        if (event.getState() != State.PENDING && event.getState() != State.CANCELED) {
            throw new IllegalArgumentEx("Изменить можно только отмененные события или события в состоянии" +
                    " ожидания модерации");
        }
    }

    public void validateEventOwner(Long userId, Long eventId) throws IllegalArgumentEx {
        if (eventRepositoryJpa.findById(eventId).get().getInitiator().getId()!=userId) {
            throw new IllegalArgumentEx("Вы не являетесь инициатором события");
        }
    }

    public void validateEventStatePending(Long eventId) throws IllegalArgumentEx {
        Event event = eventRepositoryJpa.findById(eventId).get();
        if (event.getState() != State.PENDING ) {
            throw new IllegalArgumentEx("Отменить можно только событие в состоянии ожидания модерации");
        }
    }

    public void validateCompilation(Long compilationId) throws NotFoundEx {
        if (compilationRepositoryJpa.findById(compilationId).isEmpty()) {
            throw new NotFoundEx("Подборка не найдена", compilationId);
        }
    }
    public void validateUser(Long userId) throws NotFoundEx {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundEx("Пользователь не найден", userId);
        }
    }

    public void validateRequest(Long requestId) throws NotFoundEx {
        if (requestRepositoryJpa.findById(requestId).isEmpty()) {
            throw new NotFoundEx("Запрос на участие не найден", requestId);
        }
    }
    public void validateCategory(Long categoryId) throws NotFoundEx {
        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw new NotFoundEx("Категория не найдена", categoryId);
        }
    }

    public void validateEvent(Long eventId) throws NotFoundEx {
        if (eventRepositoryJpa.findById(eventId).isEmpty()) {
            throw new NotFoundEx("Такого события в базе нет ", eventId);
        }
    }

    public void sort(String sort) throws IllegalArgumentEx {
        if (!sort.equals("EVENT_DATE") || !sort.equals("VIEWS")) {
            throw new IllegalArgumentEx("Wrong sort");
        }
    }

  /*  public void validateUserIsBookerOrOwner(Booking booking, long userId) throws NotFoundEx {
        long bookerId = booking.getBooker().getId();
        long ownerId = booking.getItem().getOwner().getId();
        if ((bookerId != userId) && (ownerId != userId))
            throw new NotFoundEx("User is not booker");
    }

   */



/*
    public void validateItem(Long itemId) throws NotFoundEx {
        if (itemRepository.findById(itemId).isEmpty()) {
            throw new NotFoundEx("Item not found");
        }
    }

    public void validateItemAvailable(Long itemId) throws IllegalArgumentEx {
        if (!itemRepository.findById(itemId).get().getAvailable()) {
            throw new IllegalArgumentEx("Item not available");
        }
    }

    public void validateBookingDate(BookingDto bookingDto) throws IllegalArgumentEx {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new IllegalArgumentEx("Item date wrong");
        }
    }

    public void validateBooking(Long bookingId) throws NotFoundEx {
        if (bookingRepository.findById(bookingId).isEmpty()) {
            throw new NotFoundEx("Booking not found");
        }
    }

    public void validateItemOwner(Item item, long userId) throws NotFoundEx {
        if (item.getOwner().getId() != userId) {
            throw new NotFoundEx("User is not owner");
        }
    }

    public void validateBookerIsOwner(Item item, long userId) throws NotFoundEx {
        if (item.getOwner().getId() == userId) {
            throw new NotFoundEx("the owner cannot book his item");
        }
    }

 */

    public void validatePagination(int from, int size) throws IllegalArgumentEx {
        if (from < 0) {
            throw new IllegalArgumentEx("Argument from incorrect");
        } else if (size <= 0) {
            throw new IllegalArgumentEx("Argument size incorrect");
        }
    }
}
