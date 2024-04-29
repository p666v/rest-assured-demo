package ru.buttonone;

import io.qameta.allure.restassured.AllureRestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.buttonone.constants.ApiConstant.*;
import static ru.buttonone.specifications.Specification.*;

@Slf4j
public class PetStoreTest {

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Проверка поиска питомцев по статусу")
    public void checkPetSearchByStatus(String argument) {
        log.info(String.format("Проверка поиска питомцев по статусу %s", argument));
        given()
                .spec(requestSpecWithStatus(argument))
                .when()
                .get(PET_BY_STATUS)
                .then()
                .body(matchesJsonSchemaInClasspath("pet-schema.json"))
                .spec(responseSpec());
        log.info("Проверка поиска питомцев по статусу успешна");
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 9})  //todo Нет доступа к базе данных, данные могут отсутствовать
    @DisplayName("Проверка наличия заказа по идентификатору")
    public void checkThatOrderExistsById(int argument) {
        log.info(String.format("Проверка наличия заказа по идентификатору %d", argument));
        given()
                .spec(requestSpecParamsId(argument))
                .when()
                .get(ORDER_BY_ID)
                .then()
                .body(matchesJsonSchemaInClasspath("order-schema.json"))
                .spec(responseSpec());
        log.info("Проверка наличия заказа по идентификатору успешна");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 9, 420})  //todo Нет доступа к базе данных, данные могут отсутствовать
    @DisplayName("Проверка питомца по идентификатору")
    public void checkPetById(int argument) {
        log.info(String.format("Проверка питомца по идентификатору %d", argument));
        given()
                .spec(requestSpecParamsId(argument))
                .when()
                .get(PET_BY_ID)
                .then()
                .body(matchesJsonSchemaInClasspath("pet-schema.json"))
                .spec(responseSpec());
        log.info("Проверка питомца по идентификатору успешна");
    }

    @Test
    @DisplayName("Проверка получения инвентаря питомцев")
    public void checkByInventory() {
        log.info("Проверка получения инвентаря питомцев");
        given()
                .baseUri(BASE_URL)
                .filter(new AllureRestAssured())
                .when()
                .get(PET_BY_INVENTORY)
                .then()
                .spec(responseSpec());
        log.info("Проверка получения инвентаря питомцев успешна");
    }
}