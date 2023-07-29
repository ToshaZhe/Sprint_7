package ru.yandex.praktikum.qa_scooter.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.yandex.praktikum.qa_scooter.configs.Config;

public class RestClient {
    public RequestSpecification getDefaultRequestSpec() {
        return new RequestSpecBuilder().setBaseUri(Config.getBaseUrl()).setContentType(ContentType.JSON).build();
    }
}
