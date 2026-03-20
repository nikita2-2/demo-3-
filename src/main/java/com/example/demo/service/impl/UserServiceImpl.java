package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service  // 👈 Говорим Spring'у: этот класс — сервис
public class UserServiceImpl implements UserService {  // 👈 Реализуем интерфейс

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ---------- Конвертеры (раньше были в контроллере) ----------
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        return user;
    }

    // ---------- Реализация методов интерфейса ----------

    @Override
    public UserDto createUser(UserDto userDto) {
        // Проверяем, есть ли уже пользователь с таким email
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Пользователь с email " + userDto.getEmail() + " уже существует");
        }

        // Преобразуем DTO в сущность
        User user = convertToEntity(userDto);

        // Сохраняем в БД
        User savedUser = userRepository.save(user);

        // Преобразуем обратно в DTO и возвращаем
        return convertToDto(savedUser);
    }
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return convertToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Пользователь с email " + email + " не найден");
        }
        return convertToDto(user);
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Если email меняется — проверяем уникальность
        if (!user.getEmail().equals(userDto.getEmail()) &&
                userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email уже используется");
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Пользователь не найден");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }
        userRepository.delete(user);
    }
}