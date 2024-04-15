package ru.buttonone;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.buttonone.pojo.Pet;
import ru.buttonone.utilities.ConfProperties;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.buttonone.EndPoints.*;
import static ru.buttonone.specifications.Specification.*;


public class PetStoreTest {
    private final ConfProperties confProperties = new ConfProperties();
    private final PetStoreTestData petStoreTestData = new PetStoreTestData();

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Проверка поиска питомцев по статусу")
    public void checkPetSearchByStatus(String argument) {
        given()
                .spec(requestSpecParamsStatus(confProperties.getProperty("base-url"), argument))
                .when()
                .get(PET_STATUS)
                .then()
                .spec(responseSpec());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 9, 10})
    @DisplayName("Проверка наличия заказа по идентификатору")
    public void checkThatOrderExistsById(int argument) {
        given()
                .spec(requestSpecParamsId(confProperties.getProperty("base-url"), argument))
                .when()
                .get(ORDER_ID)
                .then()
                .spec(responseSpec());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 11, 27})
    @DisplayName("Проверка питомца по идентификатору")
    public void checkPetById(int argument) {
        given()
                .spec(requestSpecParamsId(confProperties.getProperty("base-url"), argument))
                .when()
                .get(PET_ID)
                .then()
                .spec(responseSpec());
    }

    @Test
    @DisplayName("Проверка получения инвентаря питомцев")
    public void checkByInventory() {
        given()
                .baseUri(confProperties.getProperty("base-url"))
                .when()
                .get(PET_INVENTORY)
                .then()
                .spec(responseSpec());
    }

    @Test
    @DisplayName("Проверка добавления нового питомца в магазин")
    public void checkAddNewPetToStore() {
        Pet response = given()
                .baseUri(confProperties.getProperty("base-url"))
                .when()
                .contentType(ContentType.JSON)
                .log().all()
                .body(petStoreTestData.petData())
                .post(PET_ADD)
                .then()
                .log().all()
                .spec(responseSpec())
                .extract().body().as(Pet.class);
        Assertions.assertAll(
                () -> assertEquals(petStoreTestData.petData().getId(), response.getId(),
                        "ID указанный в теле ответа не соответствует ID в POST запросе"),
                () -> assertEquals(petStoreTestData.categoryData(), response.getCategory(),
                        "Категория указанная в теле ответа не соответствует Категории в POST запросе")
        );
    }
}