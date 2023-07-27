package ru.yandex.praktikum.qa_scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.qa_scooter.client.CourierClient;
import ru.yandex.praktikum.qa_scooter.pojo.CourierRequest;
import ru.yandex.praktikum.qa_scooter.pojo.LoginRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.qa_scooter.generation.CourierRequestGenerator.getRandomCourierRequest;
import static ru.yandex.praktikum.qa_scooter.generation.LoginRequestGenerator.getLoginRequest;

public class CourierLoginTest {
    private CourierClient courierClient;
    private CourierRequest randomCourierRequest;
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        randomCourierRequest = getRandomCourierRequest();
        courierClient.createCourier(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.deleteCourier(id).assertThat().statusCode(SC_OK).and().body("ok", equalTo(true));
        }
    }

    @DisplayName("Авторизация курьера с корректными параметрами")
    @Test
    public void courierAuthCorrectParamShouldBeAuthorizedTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        id = courierClient.loginCourier(loginRequest).assertThat().statusCode(SC_OK).and().body("id", notNullValue()).extract().path("id");
    }

    @DisplayName("Авторизация курьера с пустым логином")
    @Test
    public void courierAuthLoginIsEmptyShouldNotBeAuthorizedTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setLogin("");
        courierClient.loginCourier(loginRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Авторизация курьера с пустым паролем")
    @Test
    public void courierAuthPasswordIsEmptyShouldNotBeAuthorizedTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setPassword("");
        courierClient.loginCourier(loginRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Авторизация курьера с неверным логином")
    @Test
    public void courierAuthIncorrectLoginShouldNotBeAuthorizedTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setLogin("badCourier");
        courierClient.loginCourier(loginRequest).assertThat().statusCode(SC_NOT_FOUND).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Авторизация курьера c неверным паролем")
    @Test
    public void courierAuthIncorrectPasswordShouldNotBeAuthorizedTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setPassword("qwerty");
        courierClient.loginCourier(loginRequest).assertThat().statusCode(SC_NOT_FOUND).and().body("message", equalTo("Учетная запись не найдена"));
    }
}