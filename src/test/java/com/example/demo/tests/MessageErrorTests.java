package com.example.demo.tests;

import com.example.demo.controller.request.AuthRequest;
import com.example.demo.controller.request.ChangeCommentRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MessageErrorTests {

    private static final String STUDENT = "polka@mail.ru";
    private static final String ADMIN = "admin1@mail.ru";

    private String getAccessToken(String email) {
        AuthRequest authRequest = new AuthRequest(email, "12345678");
        return RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.OK.value()).extract().body().jsonPath().getString("accessToken");
    }


    /** @GetMapping("/student/messageError") */
    @Test
    public void messagesErrorForStudentTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/messageError")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/student/messageError/{messageErrorId}") */
    @Test
    public void messageErrorByIdForStudentTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/messageError/{messageErrorId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("messageErrorId", "2")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void messageErrorByIdForStudentTestStatus404() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/messageError/{messageErrorId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("messageErrorId", "1")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Сообщение об ошибке с указанным id не найдено или принадлежит другому студенту"));
    }


    /** @GetMapping("/admin/statusMessageError") */
    @Test
    public void statusMessageErrorForAdminTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/statusMessageError")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/admin/messageError") */
    @Test
    public void messagesErrorForAdminTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/messageError")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void messagesErrorByStatusForAdminTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/messageError")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("statusId", "2")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void messagesErrorByStatusForAdminTestStatus404() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/messageError")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("statusId", "999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Статус обработки сообщения об ошибке с указанными id не найден"));

    }


    /** @GetMapping("/admin/messageError/{errorId}") */
    @Test
    public void messageErrorByIdForAdminTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/messageError/{errorId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("errorId", "1")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void messageErrorByIdForAdminTestStatus404() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/messageError/{errorId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("errorId", "99999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    /** @PutMapping("/admin/changeCommentMessageError") */
    @Test
    public void changeCommentMessageErrorForAdminTestStatus404() {
        String accessToken = getAccessToken(ADMIN);
        ChangeCommentRequest changeCommentRequest = new ChangeCommentRequest(99999, "123");
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/changeCommentMessageError")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .body(changeCommentRequest)
                .when().log().body().put()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    /** @PutMapping("/admin/changeStatusMessageError/{messageId}/{statusId}") */
    @Test
    public void changeStatusMessageErrorForAdminTestStatus404MessageNotExist() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/changeStatusMessageError/{messageId}/{statusId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("messageId", "99999")
                .pathParam("statusId", "1")
                .when().put()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Сообщение об ошибке с указанным id не найдено"));
    }

    @Test
    public void changeStatusMessageErrorForAdminTestStatus404StatusNotExist() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/changeStatusMessageError/{messageId}/{statusId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("messageId", "1")
                .pathParam("statusId", "9999")
                .when().put()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Статус обработки сообщения об ошибке с указанным id не найден"));
    }
}
