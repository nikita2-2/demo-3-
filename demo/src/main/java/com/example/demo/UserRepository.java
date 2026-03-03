package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// JpaRepository<User, Long> означает:
// - User: с какой сущностью работаем
// - Long: тип первичного ключа
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA сам реализует эти методы по их названиям!

    // Найти пользователя по email (вернет одного, т.к. email уникальный)
    User findByEmail(String email);

    // Найти всех пользователей с указанным именем
    List<User> findByName(String name);

    // Найти пользователей старше определенного возраста
    List<User> findByAgeGreaterThan(Integer age);

    // Можно добавить и другие методы:
    // List<User> findByAgeLessThan(Integer age);
    // List<User> findByNameContaining(String part);
    // boolean existsByEmail(String email);
}