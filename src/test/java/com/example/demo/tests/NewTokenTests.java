package com.example.demo.tests;

import com.example.demo.controller.request.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class NewTokenTests {

    /** @GetMapping("/newToken") */
    @Test
    public void newTokenStatus200() {
        AuthRequest authRequest = new AuthRequest("polka@mail.ru", "12345678");
        String refreshToken = RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.OK.value()).extract().body().jsonPath().getString("refreshToken");

        RestAssured.given().baseUri("http://localhost:8080").basePath("/newToken")
                .contentType(ContentType.JSON).header("Refresh", "Refresh "+refreshToken)
                .when().log().headers().get()
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void newTokenStatus403NullRefreshToken() {
        RestAssured.given().baseUri("http://localhost:8080").basePath("/newToken")
                .when().log().headers().get()
                .then().log().body()
                .statusCode(HttpStatus.FORBIDDEN.value()).and().body("message", IsEqual.equalTo("Рефреш токен пуст"));
    }

    @Test
    public void newTokenStatus403InvalidRefreshToken() {
        AuthRequest authRequest = new AuthRequest("polka@mail.ru", "12345678");
        String refreshToken = RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.OK.value()).extract().body().jsonPath().getString("refreshToken");

        RestAssured.given().baseUri("http://localhost:8080").basePath("/newToken")
                .contentType(ContentType.JSON).header("Refresh", "Refresh "+refreshToken.substring(0,160))
                .when().log().headers().get()
                .then().log().body()
                .statusCode(HttpStatus.FORBIDDEN.value()).and().body("message", IsEqual.equalTo("Рефреш токен недействителен"));
    }
}
