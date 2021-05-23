package com.example.demo.tests;

import com.example.demo.controller.request.RegistrationUserRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class RegisterTests {

    /** @PostMapping("/register")
     * Аналогичные проверки для
     * @PostMapping("/admin/registerAdmin")
     * @PostMapping("/admin/registerModer")
     * */

    @Test
    public void registerStudentTestStatus404InvalidEmail() {
        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest("test1@@@@mail.ru", "12345678", "Тест", "Тест", 1, 1, 1, 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/register").contentType(ContentType.JSON).body(registrationUserRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("message", IsEqual.equalTo("Email введен не корректно"));

    }

    @Test
    public void registerStudentTestStatus404InvalidPassword() {
        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest("test123@mail.ru", "123456", "Тест", "Тест", 1, 1, 1, 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/register").contentType(ContentType.JSON).body(registrationUserRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("message", IsEqual.equalTo("Пароль должен состоять не менее чем из 8 символов"));

    }

    @Test
    public void registerTestStatus404InvalidFirstNameNotCyrillic() {
        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest("test123@mail.ru", "12345678", "Test", "Тест", 1, 1, 1, 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/register").contentType(ContentType.JSON).body(registrationUserRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("message", IsEqual.equalTo("Для фамилии и имени разрешено использовать только русские буквы. Минимальная длина составляет 2 символа"));

    }

    @Test
    public void registerTestStatus404InvalidLastNameNotCyrillic() {
        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest("test123@mail.ru", "12345678", "Тест", "ТестTest", 1, 1, 1, 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/register").contentType(ContentType.JSON).body(registrationUserRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("message", IsEqual.equalTo("Для фамилии и имени разрешено использовать только русские буквы. Минимальная длина составляет 2 символа"));

    }

    @Test
    public void registerTestStatus404InvalidFirstNameTooShort() {
        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest("test123@mail.ru", "12345678", "Т", "Тест", 1, 1, 1, 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/register").contentType(ContentType.JSON).body(registrationUserRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("message", IsEqual.equalTo("Для фамилии и имени разрешено использовать только русские буквы. Минимальная длина составляет 2 символа"));

    }

    @Test
    public void registerTestStatus404InvalidLastNameTooShort() {
        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest("test123@mail.ru", "12345678", "Тест", "Т", 1, 1, 1, 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/register").contentType(ContentType.JSON).body(registrationUserRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value()).and().body("message", IsEqual.equalTo("Для фамилии и имени разрешено использовать только русские буквы. Минимальная длина составляет 2 символа"));

    }
}
