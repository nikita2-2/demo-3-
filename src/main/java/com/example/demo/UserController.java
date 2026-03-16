package com.example.demo;

import com.example.demo.UserDto;  // 👈 Импортировали наш DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  // 👈 Для ResponseEntity
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
                // createdAt не передаем - его нет в DTO!
        );
    }



    // ✅ СТАЛО:
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        // 1. Пробуем найти пользователя
        User user = userRepository.findById(id).orElse(null);

        // 2. Если нет - 404 Not Found
        if (user == null) {
            return ResponseEntity.notFound().build();  // 👈 Статус 404
        }

       // превращаем в DTO (без createdAt)
        UserDto dto = convertToDto(user);

        // ответ
        return ResponseEntity.ok(dto);  // 👈 Статус 200
    }


    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        // Сохраняем пользователя
        User savedUser = userRepository.save(user);

        // Превращаем в DTO
        UserDto dto = convertToDto(savedUser);

        // Возвращаем
        return ResponseEntity.status(201).body(dto);
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        // 1. Получаем всех пользователей
        List<User> users = userRepository.findAll();

        // 2. Превращаем каждого User в UserDto
        List<UserDto> dtos = users.stream()
                .map(this::convertToDto)  // для каждого user вызываем convertToDto
                .toList();                // собираем в список

        // 3. Возвращаем с 200 OK
        return ResponseEntity.ok(dtos);
    }

    // =========== GET по email ===========
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto dto = convertToDto(user);
        return ResponseEntity.ok(dto);
    }

    // =========== GET по имени ===========
    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDto>> getUsersByName(@PathVariable String name) {
        List<User> users = userRepository.findByName(name);

        List<UserDto> dtos = users.stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // =========== GET старше возраста ===========
    @GetMapping("/age/older/{age}")
    public ResponseEntity<List<UserDto>> getUsersOlderThan(@PathVariable Integer age) {
        List<User> users = userRepository.findByAgeGreaterThan(age);

        List<UserDto> dtos = users.stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    // =========== PUT - обновить пользователя ===========
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        // 1. Ищем пользователя
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Обновляем поля
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());

        // 3. Сохраняем
        User updatedUser = userRepository.save(user);

        // 4. Возвращаем DTO
        UserDto dto = convertToDto(updatedUser);
        return ResponseEntity.ok(dto);
    }

    // =========== DELETE по id ===========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Проверяем, есть ли пользователь
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();  // 404 если нет
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();  // 👈 204 No Content
    }

    // =========== DELETE по email ===========
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}