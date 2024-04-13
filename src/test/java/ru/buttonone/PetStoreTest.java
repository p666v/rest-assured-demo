package ru.buttonone;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static ru.buttonone.EndPoints.*;
import static ru.buttonone.specifications.Specification.requestSpecParamsId;
import static ru.buttonone.specifications.Specification.requestSpecParamsStatus;


public class PetStoreTest {
    private static final String BASE_URL = "https://petstore.swagger.io";

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Проверка поиска питомцев по статусу")
    public void checkPetSearchByStatus(String argument) {
        RestAssured.given()
                .spec(requestSpecParamsStatus(BASE_URL, argument))
                .when()
                .get(PET_STATUS)
                .then().log().all()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }


    @ParameterizedTest
    @ValueSource(ints = {5, 9, 10})
    @DisplayName("Проверка наличия заказа по идентификатору")
    public void checkThatOrderExistsById(int argument) {
        RestAssured.given()
                .spec(requestSpecParamsId(BASE_URL, argument))
                .when()
                .get(ORDER_ID)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 27, 30})
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

    @Test
    @DisplayName("Проверка получения инвентаря питомцев")
    public void checkByInventory() {
        RestAssured.given()
                .baseUri(BASE_URL)
                .when()
                .get(PET_INVENTORY)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}