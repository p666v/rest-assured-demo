package ru.buttonone.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

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
}
