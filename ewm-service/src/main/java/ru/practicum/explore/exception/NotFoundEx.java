package ru.practicum.explore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundEx extends RuntimeException {
    private String message;
    private long id;
}
