package ru.buttonone;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PetStoreTest {
    private static final String BASE_URL = "https://petstore.swagger.io";
    private static final String PET_STATUS = "/v2/pet/findByStatus?status={status}";
    private static final String ORDER_ID = "/v2/store/order/{id}";
    private static final String PET_ID = "/v2/pet/{id}";

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Проверка поиска питомцев по статусу")
    public void checkPetSearchByStatus(String argument) {
        RestAssured.given()
                .baseUri(BASE_URL)
                .pathParam("status", argument)
                .when()
                .get(PET_STATUS)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 6, 8})
    @DisplayName("Проверка наличия заказа по идентификатору")
    public void checkThatOrderExistsById(int argument) {
        RestAssured.given()
                .baseUri(BASE_URL)
                .pathParam("id", argument)
                .when()
                .get(ORDER_ID)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 26, 30})
    @DisplayName("Проверка питомца по идентификатору")
    public void checkPetById(int argument) {
        RestAssured.given()
                .baseUri(BASE_URL)
                .pathParam("id", argument)
                .when()
                .get(PET_ID)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }


}