package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;


@QuarkusTest
public class FileServiceTest {


    @Test
    public void testUploadFilesNoFile() {
        given()
                .when()
                .post("/files/upload")
                .then()
                .statusCode(jakarta.ws.rs.core.Response.Status.BAD_REQUEST.getStatusCode());
    }


    @Test
    public void testDownloadFileNotFound() {
        given()
                .when()
                .get("/files/download/9999")
                .then()
                .statusCode(jakarta.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode());
    }

}
