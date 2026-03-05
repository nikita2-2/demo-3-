package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {


    // Найти пользователя по email (вернет одного, т.к. email уникальный)
    User findByEmail(String email);

    // Найти всех пользователей с указанным именем
    List<User> findByName(String name);

    // Найти пользователей старше определенного возраста
    List<User> findByAgeGreaterThan(Integer age);


}