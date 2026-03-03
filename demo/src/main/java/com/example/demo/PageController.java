package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller  // Используем @Controller, а не @RestController
public class PageController {
    @Autowired  // <-- ДОБАВИТЬ ЭТУ СТРОКУ
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public String welcome(Model model) {
        // Добавляем данные в модель (они попадут в HTML)
        model.addAttribute("name", "Никита");  // Можно заменить на любое имя
        model.addAttribute("time", LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        ));
        model.addAttribute("dayOfWeek", LocalDateTime.now().getDayOfWeek().toString());

        // Возвращаем имя HTML-файла (без .html)
        return "welcome";
    }
}