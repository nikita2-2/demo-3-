package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PageController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) {

        model.addAttribute("name", "Никита");
        model.addAttribute("time", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        ));
        model.addAttribute("dayOfWeek", LocalDateTime.now().getDayOfWeek().toString());

        // Возвращаем имя HTML-файла (без .html)

        return "welcome";
    }
}