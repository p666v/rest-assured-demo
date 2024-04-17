package ru.buttonone;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.buttonone.models.Pet;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.buttonone.constants.ApiConstant.*;
import static ru.buttonone.specifications.Specification.*;

@Slf4j
public class PetStoreTest {
    private final PetStoreTestData petStoreTestData = new PetStoreTestData();

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Проверка поиска питомцев по статусу")
    public void checkPetSearchByStatus(String argument) {
        log.info("Проверка поиска питомцев по статусу");
        given()
                .spec(requestSpecWithStatus(argument))
                .when()
                .get(PET_BY_STATUS)
                .then()
                .spec(responseSpec());
        log.info("Проверка поиска питомцев по статусу успешна");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 9, 10})
    @DisplayName("Проверка наличия заказа по идентификатору")
    public void checkThatOrderExistsById(int argument) {
        log.info("Проверка наличия заказа по идентификатору");
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
        log.info("Проверка питомца по идентификатору");
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

    @Test
    @DisplayName("Проверка добавления нового питомца в магазин")
    public void checkAddNewPetToStore() {
        log.info("Проверка добавления нового питомца в магазин");
        Pet response = given()
                .baseUri(BASE_URL)
                .filter(new AllureRestAssured())
                .when()
                .contentType(ContentType.JSON)
                .log().all()
                .body(petStoreTestData.petData())
                .post(ADD_NEW_PET)
                .then()
                .log().all()
                .spec(responseSpec())
                .extract().body().as(Pet.class);

        log.info("Проверка созданного объекта на соответствие данным в POST запросе");
        Assertions.assertAll(
                () -> assertEquals(petStoreTestData.petData().getId(), response.getId(),
                        "ID указанный в теле ответа не соответствует ID в POST запросе"),
                () -> assertEquals(petStoreTestData.categoryData(), response.getCategory(),
                        "Category указанная в теле ответа не соответствует Category в POST запросе"),
                () -> assertEquals(petStoreTestData.petData().getName(), response.getName(),
                        "Name указанный в теле ответа не соответствует Name в POST запросе"),
                () -> assertEquals(petStoreTestData.petData().getPhotoUrls(), response.getPhotoUrls(),
                        "PhotoUrls указанный в теле ответа не соответствует PhotoUrls в POST запросе"),
                () -> assertEquals(petStoreTestData.petData().getTags(), response.getTags(),
                        "Tag указанный в теле ответа не соответствует Tag в POST запросе"),
                () -> assertEquals(petStoreTestData.petData().getStatus(), response.getStatus(),
                        "Status указанный в теле ответа не соответствует Status в POST запросе")
        );
        log.info("Объект соответствует данным в POST запросе");
    }
}