package com.codewithjay.Services;

import com.codewithjay.Dto.UserDto;

import java.util.List;

public interface UserServices {

    UserDto addUser(UserDto userDto);
    UserDto updateUser(String id , UserDto userDto);
    void deleteUser(String id);
    List<UserDto> getAllUsers();
    UserDto getUser(String id);

}
