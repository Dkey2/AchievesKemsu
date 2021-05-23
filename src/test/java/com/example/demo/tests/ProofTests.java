package com.example.demo.tests;

import com.example.demo.controller.request.AuthRequest;
import com.example.demo.controller.request.CreationProofRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ProofTests {

    private static final String STUDENT = "polka@mail.ru";
    private static final String MODER = "moder1@gmail.com";

    private String getAccessToken(String email) {
        AuthRequest authRequest = new AuthRequest(email, "12345678");
        return RestAssured.given().baseUri("http://localhost:8080").basePath("/auth").contentType(ContentType.JSON).body(authRequest)
                .when().log().body().post()
                .then().log().body()
                .statusCode(HttpStatus.OK.value()).extract().body().jsonPath().getString("accessToken");
    }


    /** @GetMapping("/student/proof") */
    @Test
    public void proofsForStudentTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/proof")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    /** @GetMapping("/student/proof/{proofId}") */
    @Test
    public void proofByIdForStudentTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/proof/{proofId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("proofId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void proofByIdForStudentTestStatus404() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/proof/{proofId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("proofId", "1")
                .when().get()
                .then().log().body()
                .statusCode(HttpStatus.NOT_FOUND.value()).and().body("message", IsEqual.equalTo("Подтверждение с указанным id не найдено или принадлежит другому студенту"));
    }


    /** @GetMapping("/student/listFile/{listFileId}") */
    @Test
    public void filesForListFileForStudentTestStatus200() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/listFile/{listFileId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("listFileId", "3")
                .when().get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void filesForListFileForStudentTestStatus404() {
        String accessToken = getAccessToken(STUDENT);
        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/listFile/{listFileId}")
                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
                .pathParam("listFileId", "99999")
                .when().get()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

//    /** @PostMapping("/student/newProof") */
//    @Test
//    public void newProofForStudentTestStatus409AlreadyReceived() {
//        String accessToken = getAccessToken(STUDENT);
//        CreationProofRequest creationProofRequest = new CreationProofRequest("Хочу это достижение", 0, 2);
//        RestAssured.given().baseUri("http://localhost:8080").basePath("/student/newProof")
//                .contentType(ContentType.JSON).header("AUTHORIZATION", "Bearer "+accessToken)
//                .body(creationProofRequest)
//                .when().log().body().post()
//                .then().log().body()
//                .statusCode(HttpStatus.CONFLICT.value()).and().body("message", IsEqual.equalTo("Подтверждение с указанным id не найдено или принадлежит другому студенту"));
//    }
}
