package ru.buttonone;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import ru.buttonone.models.Pet;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.buttonone.constants.ApiConstant.ADD_NEW_PET;
import static ru.buttonone.constants.ApiConstant.BASE_URL;
import static ru.buttonone.specifications.Specification.responseSpec;

@Isolated
@Slf4j
public class AddNewPetToStoreTest {
    private final PetStoreTestData petStoreTestData = new PetStoreTestData();

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
        Pet petData = petStoreTestData.petData();

        assertAll(
                () -> assertEquals(petData.getId(), response.getId(),
                        "ID указанный в теле ответа не соответствует ID в POST запросе"),
                () -> assertEquals(petData.getCategory(), response.getCategory(),
                        "Category указанная в теле ответа не соответствует Category в POST запросе"),
                () -> assertEquals(petData.getName(), response.getName(),
                        "Name указанный в теле ответа не соответствует Name в POST запросе"),
                () -> assertEquals(petData.getPhotoUrls(), response.getPhotoUrls(),
                        "PhotoUrls указанный в теле ответа не соответствует PhotoUrls в POST запросе"),
                () -> assertEquals(petData.getTags(), response.getTags(),
                        "Tag указанный в теле ответа не соответствует Tag в POST запросе"),
                () -> assertEquals(petData.getStatus(), response.getStatus(),
                        "Status указанный в теле ответа не соответствует Status в POST запросе")
        );
        log.info("Объект соответствует данным в POST запросе");
    }
}
