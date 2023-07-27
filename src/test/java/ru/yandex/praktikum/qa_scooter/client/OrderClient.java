package ru.yandex.praktikum.qa_scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.qa_scooter.pojo.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    public static final String ORDER = "orders";

    @Step("Размещение заказа")
    public ValidatableResponse createOrder(OrderRequest orderRequest) {
        return given().spec(getDefaultRequestSpec()).body(orderRequest).post(ORDER).then();
    }

    @Step("Получение заказа по его номеру")
    public ValidatableResponse getOrderList(OrderRequest orderRequest) {
        return given().spec(getDefaultRequestSpec()).body(orderRequest).get(ORDER).then();
    }
}
