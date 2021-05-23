package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class SecurityController {

    //Тестовые запросы. Проверка на прохождение авторизации и валидацию токена.
    @GetMapping("/student/get")
    public String getStudent() {
        return "Привет, студент";
    }

    @GetMapping("/admin/get")
    public String getAdmin() {
        return "Привет, админ";
    }

    @GetMapping("/moderator/get")
    public String getModerator() {
        return "Привет, модератор";
    }
}
