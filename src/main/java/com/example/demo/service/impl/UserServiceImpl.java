package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 👈 Lombok: создаст конструктор для всех final полей
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // 👇 РУЧНОЙ КОНСТРУКТОР УДАЛЯЕМ (Lombok сгенерирует сам)

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userMapper.toEntity(userDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedUserEntity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return userMapper.toDto(userEntity);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new RuntimeException("Пользователь с email " + email + " не найден");
        }
        return userMapper.toDto(userEntity);
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!userEntity.getEmail().equals(userDto.getEmail()) &&
                userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email уже используется");
        }

        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setAge(userDto.getAge());

        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(updatedUserEntity);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Пользователь не найден");
        }
        userRepository.deleteById(id);
    }
}