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

    @Step("Создание курьера. Логин: {courierRequest.login} Имя: {courierRequest.firstName}")
    public ValidatableResponse createCourier(CourierRequest courierRequest) {
        return given().spec(getDefaultRequestSpec()).body(courierRequest).post(COURIER).then();
    }

    @Step("Авторизация курьера. Логин: {loginRequest.login}")
    public ValidatableResponse loginCourier(LoginRequest loginRequest) {
        return given().spec(getDefaultRequestSpec()).body(loginRequest).post(COURIER_LOGIN).then();
    }

    @Step("Удаление курьера: {id}")
    public ValidatableResponse deleteCourier(int id) {
        return given().spec(getDefaultRequestSpec()).delete(COURIER_DELETE, id).then();
    }
}