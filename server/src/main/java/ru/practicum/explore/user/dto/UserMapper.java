package ru.practicum.explore.user.dto;

import ru.practicum.explore.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        if (user == null) return null;
        else return new UserDto(
                user.getName(),
                user.getEmail()
        );
    }

    public static UserDtoAnswer toUserDtoAnswer(User user) {
        if (user == null) return null;
        else return new UserDtoAnswer(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toUser(UserDto userDto) {
        if (userDto == null) return null;
        else return new User(
                0L,
                userDto.getName(),
                userDto.getEmail()
        );
    }
}
