package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(Long[] ids, Integer from, Integer size);

    UserDto createUser(UserDto userDto);

    void deleteUserById(Long userId);
}
