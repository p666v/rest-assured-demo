package ru.buttonone;

import io.qameta.allure.restassured.AllureRestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
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
                .spec(responseSpec());
        log.info("Проверка поиска питомцев по статусу успешна");
    }

    @ParameterizedTest
    @ValueSource(ints = {50, 55, 10})
    @DisplayName("Проверка наличия заказа по идентификатору")
    public void checkThatOrderExistsById(int argument) {
        log.info(String.format("Проверка наличия заказа по идентификатору %d", argument));
        given()
                .spec(requestSpecParamsId(argument))
                .when()
                .get(ORDER_BY_ID)
                .then()
                .spec(responseSpec());
        log.info("Проверка наличия заказа по идентификатору успешна");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 27})
    @DisplayName("Проверка питомца по идентификатору")
    public void checkPetById(int argument) {
        log.info(String.format("Проверка питомца по идентификатору %d", argument));
        given()
                .spec(requestSpecParamsId(argument))
                .when()
                .get(PET_BY_ID)
                .then()
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