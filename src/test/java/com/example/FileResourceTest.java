package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
public class FileResourceTest {

    @Test
    public void testHello() {
        given()
                .when()
                .get("/files/say_hello")
                .then()
                .statusCode(jakarta.ws.rs.core.Response.Status.OK.getStatusCode())
                .body(is("welcome to pdf file services!"));
    }


    @Test
    public void testUploadFilesNoFile() {
        given()
                .when()
                .post("/files/upload")
                .then()
                .statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .body(is("Error uploading files."));
    }


    @Test
    public void testDownloadFileNotFound() {
        given()
                .when()
                .get("/files/download/9999")
                .then()
                .statusCode(jakarta.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testListFiles() {
        given()
                .when()
                .get("/files/all")
                .then()
                .statusCode(jakarta.ws.rs.core.Response.Status.OK.getStatusCode())
                .contentType(ContentType.JSON)
                .body("size()", is(10)); //based on case saya sendiri
    }

}
