package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class HelloController {


    @GetMapping("/api/hello")
    public String simpleHello() {
        return "Привет от API!";
    }


    @GetMapping("/")
    public String home() {
        return """
                <h1>Добро пожаловать!</h1>
                <p>Сервер работает!</p>
                <p>Попробуйте <a href="/api/hello">/api/hello</a></p>
                """;
    }

    @GetMapping("/info")
    public String info() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return """
                {
                    "appName": "Мое Spring приложение",
                    "version": "1.0.0",
                    "serverTime": "%s",
                    "javaVersion": "%s"
                }
                """.formatted(
                now.format(formatter),
                System.getProperty("java.version")
        );
    }
}