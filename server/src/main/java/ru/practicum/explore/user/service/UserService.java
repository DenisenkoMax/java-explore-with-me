package ru.practicum.explore.user.service;

import ru.practicum.explore.exception.IllegalArgumentEx;
import ru.practicum.explore.exception.NotFoundEx;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserDtoAnswer;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDtoAnswer> getAllUsers(Long[] ids, int from, int size) throws IllegalArgumentEx;

    Optional<UserDtoAnswer> createUser(UserDto userDto);

    void deleteUserById(long id) throws NotFoundEx;


}
