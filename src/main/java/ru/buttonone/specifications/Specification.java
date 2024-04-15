package ru.buttonone.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {

    public static RequestSpecification requestSpecParamsStatus(String url, String argument) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .log(LogDetail.ALL)
                .addPathParams("status", argument)
                .build();
    }

    public static RequestSpecification requestSpecParamsId(String url, int argument) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .log(LogDetail.ALL)
                .addPathParams("id", argument)
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