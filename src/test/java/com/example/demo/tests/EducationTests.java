package com.example.demo.tests;

import com.example.demo.controller.request.AuthRequest;
import com.example.demo.controller.request.CreationGroupRequest;
import com.example.demo.controller.request.CreationStreamRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class EducationTests {

    private static final String STUDENT = "polka@mail.ru";
    private static final String ADMIN = "admin1@mail.ru";
    private static final String MODER = "moder1@gmail.com";

    private String getAccessToken(String email) {
        AuthRequest authRequest = new AuthRequest(email, "12345678");
        return RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.OK.value()).extract().body().jsonPath().getString("accessToken");
    }


    /** @GetMapping("/education/formEducation") */
    @Test
    public void formEducationTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/education/formEducation")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/education/institutions") */
    @Test
    public void institutionsTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/education/institutions")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/education/stream/{instituteId}") */
    @Test
    public void streamsForInstituteTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/education/stream/{instituteId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("instituteId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/education/stream/{instituteId}") */
    @Test
    public void streamsForInstituteTestStatus404InstituteNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/education/stream/{instituteId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("instituteId", "16")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    /** @GetMapping("/education/group/{streamId}") */
    @Test
    public void groupsForStreamTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/education/group/{streamId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("streamId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void groupsForStreamTestStatus404StreamNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/education/group/{streamId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("streamId", "999999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    /** @GetMapping("/moderator/institutionsList/{listInstituteId}") */
    @Test
    public void institutionsListByIdTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/institutionsList/{listInstituteId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("listInstituteId", "1")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void institutionsListByIdTestStatus404ListNotExist() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/moderator/institutionsList/{listInstituteId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("listInstituteId", "99999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    /** @GetMapping("/moderator/institutionsList") */
    @Test
    public void institutionsListTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/institutionsList")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @PostMapping("/admin/createStream") */
    @Test
    public void createStreamTestStatus409() {
        String accessToken = getAccessToken(ADMIN);
        CreationStreamRequest creationStreamRequest = new CreationStreamRequest("04.04.01 Х", "Химия", 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/createStream")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .body(creationStreamRequest)
                .when().post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value()).and().body("message", IsEqual.equalTo("Направление с указанным названием уже существует"));
    }

    @Test
    public void createStreamTestStatus404() {
        String accessToken = getAccessToken(ADMIN);
        CreationStreamRequest creationStreamRequest = new CreationStreamRequest("04.04.09 Х", "Химия", 19);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/createStream")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .body(creationStreamRequest)
                .when().post()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Институт с указанным id не найден"));
    }


    /** @PostMapping("/admin/createGroup") */
    @Test
    public void createGroupTestStatus409() {
        String accessToken = getAccessToken(ADMIN);
        CreationGroupRequest creationGroupRequest = new CreationGroupRequest("М-174", 1);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/createGroup")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .body(creationGroupRequest)
                .when().post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value()).and().body("message", IsEqual.equalTo("Группа с указанным названием уже существует"));
    }

    @Test
    public void createGroupTestStatus404() {
        String accessToken = getAccessToken(ADMIN);
        CreationGroupRequest creationGroupRequest = new CreationGroupRequest("М-174", 9999);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/createGroup")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .body(creationGroupRequest)
                .when().post()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Направление с указанным id не найдено"));
    }
}
