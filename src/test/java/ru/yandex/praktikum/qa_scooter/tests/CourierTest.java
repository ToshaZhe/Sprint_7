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
import static ru.yandex.praktikum.qa_scooter.generation.CourierRequestGenerator.*;
import static ru.yandex.praktikum.qa_scooter.generation.LoginRequestGenerator.getLoginRequest;

public class CourierTest {
    private CourierClient courierClient;
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.deleteCourier(id).assertThat().statusCode(SC_OK).and().body("ok", equalTo(true));
        }
    }

    @Test
    @DisplayName("Создание курьера, получение id")
    public void courierCreatingCorrectParamShouldBeCreatedTest() {
        CourierRequest randomCourierRequest = getCourierRequestWithoutFirstName();
        courierClient.createCourier(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));

        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);

        id = courierClient.loginCourier(loginRequest).assertThat().statusCode(SC_OK).and().body("id", notNullValue()).extract().path("id");
    }

    @DisplayName("Создание курьера c существующим логином")
    @Test
    public void courierCreatingNotUniqueLoginShouldNotBeCreatedTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.createCourier(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));
        courierClient.createCourier(randomCourierRequest).assertThat().statusCode(SC_CONFLICT).and().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Создание курьера с пустым логином")
    @Test
    public void courierCreatingLoginIsNullShouldNotBeCreatedTest() {

        CourierRequest courierRequest = getCourierRequestWithoutLogin();
        courierClient.createCourier(courierRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Создание курьера с пустым паролем")
    @Test
    public void courierCreatingPasswordIsNullShouldNotBeCreatedTest() {

        CourierRequest courierRequest = getCourierRequestWithoutPassword();
        courierClient.createCourier(courierRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым именем")
    public void courierCreatingFirstNameIsNullShouldBeCreatedTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.createCourier(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));

        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        id = courierClient.loginCourier(loginRequest).assertThat().statusCode(SC_OK).and().body("id", notNullValue()).extract().path("id");
    }
}
