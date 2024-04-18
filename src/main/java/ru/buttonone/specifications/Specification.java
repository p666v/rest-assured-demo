package ru.buttonone.specifications;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static ru.buttonone.constants.ApiConstant.BASE_URL;

public class Specification {

    public static RequestSpecification requestSpecWithStatus(String argument) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .log(LogDetail.ALL)
                .addPathParams("status", argument)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification requestSpecParamsId(int argument) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .log(LogDetail.ALL)
                .addPathParams("id", argument)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.STATUS)
                .log(LogDetail.BODY)
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}