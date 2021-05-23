package com.example.demo.tests;

import com.example.demo.controller.request.AuthRequest;
import com.example.demo.controller.request.CreationAchieveRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

public class AchievementTests {

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


    /** @GetMapping("/student/achievementsReceived/{statusId}") */
    @Test
    public void achievementsReceivedTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsReceived/{statusId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("statusId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsReceivedTestStatus404InvalidStatus() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsReceived/{statusId}").contentType(ContentType.JSON)
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("statusId", "9")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Статус с указанным id не найден"));
    }

    @Test
    public void achievementsReceivedTestStatus404InvalidCategory() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsReceived/{statusId}").contentType(ContentType.JSON)
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("statusId", "2")
                .queryParam("categoryId", "12")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Категория с указанным id не найдена"));
    }


    /** @GetMapping("/student/achievementReceived/{achieveId}") */
    @Test
    public void achievementReceivedTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementReceived/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "2")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementReceivedTestStatus404AchieveAnotherStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementReceived/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "1")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение не найдено или принадлежит другому студенту"));
    }

    @Test
    public void achievementReceivedTestStatus404AchieveNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementReceived/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "99999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение не найдено или принадлежит другому студенту"));
    }


    /** @GetMapping("/student/achievementsUnreceived/{statusId}") */
    @Test
    public void achievementsUnreceivedTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsUnreceived/{statusId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("statusId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsUnreceivedTestStatus404InvalidStatus() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsUnreceived/{statusId}").contentType(ContentType.JSON)
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("statusId", "9")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Статус с указанным id не найден"));
    }

    @Test
    public void achievementsUnreceivedTestStatus404InvalidCategory() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsUnreceived/{statusId}").contentType(ContentType.JSON)
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("statusId", "2")
                .queryParam("categoryId", "12")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Категория с указанным id не найдена"));
    }


    /** @GetMapping("/student/achievementUnreceived/{unreceivedAchieveId}") */
    @Test
    public void achievementUnreceivedTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementUnreceived/{unreceivedAchieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("unreceivedAchieveId", "2")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementUnreceivedTestStatus404AchieveNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementUnreceived/{unreceivedAchieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("unreceivedAchieveId", "99999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение с указанным id не найдено"));
    }


    /** @GetMapping("/student/achievementsPercent") */
    @Test
    public void achievementsPercentTestStatus200AuthorizedStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsPercent")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsPercentTestStatus200AnotherStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsPercent")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("studentId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @PutMapping("/student/getReward/{achieveId}") */
    @Test
    public void getRewardTestStatus404AchieveAnotherStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/getReward/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "999999")
                .when().put()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение не найдено или принадлежит другому студенту"));
    }

    @Test
    public void getRewardTestStatus404AchieveNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/getReward/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "999999")
                .when().put()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение не найдено или принадлежит другому студенту"));
    }


    /** @GetMapping("/student/achievementsCreatedReceived") */
    @Test
    public void achievementsCreatedReceivedTestStatus200AuthorizedStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreatedReceived")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsCreatedReceivedTestStatus200AnotherStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreatedReceived")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("creatorId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsCreatedReceivedTestStatus404StudentNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreatedReceived")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("creatorId", "33333")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Студент с указанным id не найден"));
    }


    /** @GetMapping("/student/achievementsCreatedUnreceived") */
    @Test
    public void achievementsCreatedUnreceivedTestStatus200AuthorizedStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreatedUnreceived")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsCreatedUnreceivedTestStatus200AnotherStudent() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreatedUnreceived")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("creatorId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsCreatedUnreceivedTestStatus404StudentNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreatedUnreceived")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("creatorId", "33333")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Студент с указанным id не найден"));
    }


    /** @GetMapping("/student/achievementsAnotherReceived/{anotherStudentId}") */
    @Test
    public void achievementsAnotherReceivedTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsAnotherReceived/{anotherStudentId}")
                .pathParam("anotherStudentId","3")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsAnotherReceivedTestStatus404StudentNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsAnotherReceived/{anotherStudentId}")
                .pathParam("anotherStudentId","99999")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Студент с указанным id не найден"));
    }


    /**  @GetMapping("/student/achievementsAnotherUnreceived/{anotherStudentId}") */
    @Test
    public void achievementsAnotherUnreceivedTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsAnotherUnreceived/{anotherStudentId}")
                .pathParam("anotherStudentId","3")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsAnotherUnreceivedTestStatus404StudentNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsAnotherUnreceived/{anotherStudentId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("anotherStudentId","99999")
                .when().log().body().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Студент с указанным id не найден"));
    }


    /**  @PostMapping("/student/newAchieve") */
    @Test
    public void newAchieveTestStatus409InvalidAchieveName() {
        String accessToken = getAccessToken(STUDENT);
        CreationAchieveRequest creationAchieveRequest = new CreationAchieveRequest("Сказочная тайга", "Пойти в поход в тайгу", 5, LocalDate.parse("2021-01-01"), null, 1, "c2c=".getBytes(), "jpg", 3);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/newAchieve")
                .body(creationAchieveRequest)
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.CONFLICT.value()).and().body("message", IsEqual.equalTo("Достижение с таким названием уже существует"));
    }

    @Test
    public void newAchieveTestStatus409InvalidDate() {
        String accessToken = getAccessToken(STUDENT);
        CreationAchieveRequest creationAchieveRequest = new CreationAchieveRequest("Сказочный поход", "Пойти в поход в тайгу", 5, LocalDate.parse("2021-01-01"), Optional.of(LocalDate.parse("2021-01-01")), 1, "c2c=".getBytes(), "jpg", 3);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/newAchieve")
                .body(creationAchieveRequest)
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.CONFLICT.value()).and().body("message", IsEqual.equalTo("Дата окончания не может быть раньше даты начала или равняться ей"));
    }


    /**  @GetMapping("/student/achievementsCreated") */
    @Test
    public void achievementsCreatedTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreated")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /**  @GetMapping("/student/achievementsCreated/{achieveId}") */
    @Test
    public void achievementsCreatedByIdTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreated/{achieveId}")
                .pathParam("achieveId", "4")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsCreatedByIdTestStatus404AchieveNotExist() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/achievementsCreated/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId","99999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение с указанным id не найдено"));
    }

    /**  @GetMapping("/admin/getStatusAchieve") */
    @Test
    public void StatusAchieveTestStatus200() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/admin/getStatusAchieve")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /**  @GetMapping("/upr/achievements") */
    @Test
    public void achievementsTestStatus200Moder() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsTestStatus200Admin() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementsTestStatus404Admin() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("statusId", "7")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Статус не найден"));
    }

    @Test
    public void achievementsTestStatus404Moder() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .queryParam("statusId", "3")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Статус не найден"));
    }

    /**  @GetMapping("/upr/achievements/{achieveId}") */
    @Test
    public void achievementByIdTestStatus200Moder() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "5")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementByIdTestStatus200Admin() {
        String accessToken = getAccessToken(ADMIN);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "5")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void achievementByIdTestStatus404Moder() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "99999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение не найдено"));
    }

    @Test
    public void achievementByIdTestStatus404Admin() {
        String accessToken = getAccessToken(MODER);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/upr/achievements/{achieveId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("achieveId", "99999")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Достижение не найдено"));
    }
}
