package com.example.demo.service;

import com.example.demo.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto getUserByEmail(String email);

    List<UserDto> getUsersByName(String name);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);




}