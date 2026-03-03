package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity  // Говорит Spring: это будет таблица в базе данных
@Table(name = "users")  // Имя таблицы
public class User {

    @Id  // Первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоинкремент
    private Long id;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


    @Column(nullable = false)  // NOT NULL
    private String name;

    @Column(unique = true, nullable = false)  // Уникальный и NOT NULL
    private String email;

    private Integer age;  // Может быть null

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Конструктор по умолчанию (обязателен для JPA)
    public User() {}

    // Конструктор для удобного создания
    public User(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }

    // Геттеры и сеттеры (Alt+Insert → Getter and Setter → выбрать все)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}