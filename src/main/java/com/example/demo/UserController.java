package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")  // Все адреса начинаются с /api/users
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // POST /api/users - создать нового пользователя
    @PostMapping
    public User createUser(@RequestBody User user) {
        // @RequestBody берет JSON из тела запроса и превращает в User
        return userRepository.save(user);  // Сохраняем в БД
    }

    // GET /api/users - получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();  // SELECT * FROM users
    }

    // GET /api/users/5 - получить пользователя по id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        // @PathVariable берет id из адреса (из {id})
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));
    }

    // GET /api/users/email/anna@mail.com - по email
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Пользователь с email " + email + " не найден");
        }
        return user;
    }

    // GET /api/users/name/Анна - по имени
    @GetMapping("/name/{name}")
    public List<User> getUsersByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    // GET /api/users/age/older/25 - старше возраста
    @GetMapping("/age/older/{age}")
    public List<User> getUsersOlderThan(@PathVariable Integer age) {
        return userRepository.findByAgeGreaterThan(age);
    }

    // PUT /api/users/5 - обновить пользователя
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));

        // Обновляем поля
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());

        return userRepository.save(user);  // Сохраняем изменения
    }

    // DELETE /api/users/5 - удалить пользователя
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Пользователь с id " + id + " удален";
    }

    // DELETE /api/users/email/anna@mail.com - удалить по email
    @DeleteMapping("/email/{email}")
    public String deleteUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
            return "Пользователь с email " + email + " удален";
        }
        return "Пользователь с email " + email + " не найден";
    }
}