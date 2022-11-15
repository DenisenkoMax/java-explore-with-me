package ru.practicum.explore.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.repository.CategoryRepositoryJpa;
import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;
import ru.practicum.explore.user.repository.UserRepositoryJpa;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Validation {
    private final UserRepositoryJpa userRepository;
    private final CategoryRepositoryJpa categoryRepository;
 /*   private final ItemRepositoryJpa itemRepository;
    private final BookingRepositoryJpa bookingRepository;
  */

    public void validateDate(LocalDateTime start, LocalDateTime end) throws IllegalArgumentEx {
        if (!end.isBefore(start)) {
            throw new IllegalArgumentEx("Wrong date");
        }
    }
    public void validateUser(Long userId) throws NotFoundEx {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundEx("User not found");
        }
    }

    public void validateCategory(Long categoryId) throws NotFoundEx {
        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw new NotFoundEx("validateCategory not found");
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
