package ru.yandex.praktikum.qa_scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.qa_scooter.pojo.CourierRequest;
import ru.yandex.praktikum.qa_scooter.pojo.LoginRequest;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER = "courier";
    private static final String COURIER_LOGIN = "courier/login";
    private static final String COURIER_DELETE = "courier/{id}";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierRequest courierRequest) {
        return given().spec(getDefaultRequestSpec()).body(courierRequest).post(COURIER).then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(LoginRequest loginRequest) {
        return given().spec(getDefaultRequestSpec()).body(loginRequest).post(COURIER_LOGIN).then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int id) {
        return given().spec(getDefaultRequestSpec()).delete(COURIER_DELETE, id).then();
    }
}