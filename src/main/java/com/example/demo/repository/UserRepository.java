package com.example.demo.repository;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {


    // Найти пользователя по email (вернет одного, т.к. email уникальный)
    UserEntity findByEmail(String email);

    // Найти всех пользователей с указанным именем
    List<UserEntity> findByName(String name);

    // Найти пользователей старше определенного возраста
    List<UserEntity> findByAgeGreaterThan(Integer age);


}