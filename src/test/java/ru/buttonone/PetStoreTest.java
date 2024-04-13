package ru.buttonone;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.buttonone.pojo.Category;
import ru.buttonone.pojo.Pet;
import ru.buttonone.pojo.Tag;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static ru.buttonone.EndPoints.*;
import static ru.buttonone.specifications.Specification.requestSpecParamsId;
import static ru.buttonone.specifications.Specification.requestSpecParamsStatus;


public class PetStoreTest {
    private static final String BASE_URL = "https://petstore.swagger.io";

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Проверка поиска питомцев по статусу")
    public void checkPetSearchByStatus(String argument) {
        given()
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
        given()
                .spec(requestSpecParamsId(BASE_URL, argument))
                .when()
                .get(ORDER_ID)
                .then().log().all()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 27, 30})
    @DisplayName("Проверка питомца по идентификатору")
    public void checkPetById(int argument) {
        given()
                .spec(requestSpecParamsId(BASE_URL, argument))
                .when()
                .get(PET_ID)
                .then().log().all()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Проверка получения инвентаря питомцев")
    public void checkByInventory() {
        given()
                .baseUri(BASE_URL)
                .when()
                .get(PET_INVENTORY)
                .then().log().all()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("Проверка добавления нового питомца в магазин")
    public void checkAddNewPetToStore() {
        Category category = new Category(109,"DOG");
        Tag tag = new Tag(105, "DDDoG");
        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("https://dog.dog.com/dog-dog.jpg");
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);
        Pet pet = new Pet(28112013, category, "Jec", photoUrls, tags, "available");

        Pet response = given()
                .baseUri(BASE_URL)
                .when()
                .contentType(ContentType.JSON)
                .log().all()
                .body(pet)
                .post(PET_ADD)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(Pet.class);

        assertEquals("28112013", response.getId().toString());


    }
}