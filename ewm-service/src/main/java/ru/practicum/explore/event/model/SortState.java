package ru.practicum.explore.event.model;

import java.util.Optional;

public enum SortState {
    EVENT_DATE, VIEWS;

    public static Optional<SortState> from(String sortState) {
        for (SortState state : values()) {
            if (state.name().equalsIgnoreCase(sortState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
