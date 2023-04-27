package ru.kata.springRest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {

    // Вывод всех пользователей
    @GetMapping("/admin")
    public String showAllUsers() {
        return "admin";
    }

    @GetMapping("/user")
    public String showUser() {
        return "user";
    }
}
