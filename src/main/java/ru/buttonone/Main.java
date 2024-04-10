package ru.buttonone;

import static io.restassured.RestAssured.given;

public class Main {
    public static final String URI = "http://numbersapi.com";
    public static final String ID = "/{id}";

    public static void main(String[] args) {

        given()
                .baseUri(URI)
                .pathParam("id", 20)
                .when()
                .get(ID)
                .then()
                .log().all()
                .statusCode(200);
    }
}
