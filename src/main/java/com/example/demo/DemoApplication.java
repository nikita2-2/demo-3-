package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

public class DemoApplication {

    public static void main(String[] args) {
        // Сохраняем контекст приложения в переменную
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        // Простая проверка - считаем сколько объектов (бинов) создал Spring
        System.out.println("=================================");
        System.out.println("Приложение запущено!");
        System.out.println("Всего бинов в контексте: " + context.getBeanDefinitionCount());
        System.out.println("=================================");

    }
}