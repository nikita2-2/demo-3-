package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "users")
@Data                    // Lombok: геттеры, сеттеры
@NoArgsConstructor       // Lombok: конструктор без параметров
@AllArgsConstructor      // Lombok: конструктор со всеми параметрами
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private Integer age;

    @CreatedDate          // наша  настройка времени
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}