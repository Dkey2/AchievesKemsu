package com.example.demo.tests;

import com.example.demo.controller.request.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AuthTests {

    /** @PostMapping("/auth") */
    @Test
    public void authTestStatus200() {
        AuthRequest authRequest = new AuthRequest("polka@mail.ru", "12345678");
        RestAssured.given().baseUri("http://82.179.9.51:11000").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void authTestStatus404InvalidEmail() {
        AuthRequest authRequest = new AuthRequest("pol1ka@mail.ru", "12345678");
        RestAssured.given().baseUri("http://82.179.9.51:11000").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Пользователь с таким email не найден"));

    }

    @Test
    public void authTestStatus404InvalidPassword() {
        AuthRequest authRequest = new AuthRequest("polka@mail.ru", "123456789");
        RestAssured.given().baseUri("http://82.179.9.51:11000").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Неверный пароль"));

    }

    @Test
    public void authTestStatus403UserBanned() {
        AuthRequest authRequest = new AuthRequest("krut2002@gmail.com", "12345678");
        RestAssured.given().baseUri("http://82.179.9.51:11000").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.FORBIDDEN.value()).and().body("message", IsEqual.equalTo("Пользователь забанен по причине: Нам это нужно для теста, прости. Бан истечет 2025-01-01"));

    }

    @Test
    public void authTestStatus403UserDeleted() {
        AuthRequest authRequest = new AuthRequest("kotya-motya@gmail.com", "12345678");
        RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.FORBIDDEN.value()).and().body("message", IsEqual.equalTo("Пользователь удален"));

    }

    @Test
    public void authTestStatus403UserAnihilated() {
        AuthRequest authRequest = new AuthRequest("blablabla@mail.ru", "12345678");
        RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.FORBIDDEN.value()).and().body("message", IsEqual.equalTo("Пользователь удален без права восстановления"));

    }
}
