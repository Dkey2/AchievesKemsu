package com.example.demo.tests;

import com.example.demo.controller.request.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class LogTests {

    private static final String ADMIN = "admin1@mail.ru";
    private static final String MODER = "moder1@gmail.com";

    private String getAccessToken(String email) {
        AuthRequest authRequest = new AuthRequest(email, "12345678");
        return RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.OK.value()).extract().body().jsonPath().getString("accessToken");
    }


    /** @GetMapping("/moderator/getLogs") */
    @Test
    public void getLogsForModeratorTestStatus200() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/moderator/getLogs")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/upr/getLog/{logId}") */
    @Test
    public void getLogByIdTestStatus404() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/getLog/{logId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("logId", "9999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    /** @GetMapping("/admin/getLogs") */
    @Test
    public void getLogsForAdminTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/getLogs")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("operationId", "1")
                .queryParam("roleId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/admin/getTypeOperation") */
    @Test
    public void getTypeOperationForAdminTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/getTypeOperation")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
