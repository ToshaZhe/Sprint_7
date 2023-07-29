package ru.yandex.praktikum.qa_scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.qa_scooter.client.OrderClient;
import ru.yandex.praktikum.qa_scooter.pojo.OrderRequest;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.qa_scooter.generation.OrderRequestGenerator.getCreateOrderRequest;

@RunWith(Parameterized.class)
public class OrderParameterizedColorTest {
    private OrderClient orderClient;

    private String[] color;

    public OrderParameterizedColorTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{{new String[]{"BLACK"}}, {new String[]{"GRAY"}}, {new String[]{"BLACK", "GRAY"}}, {new String[]{""}}};
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @DisplayName("Создание заказа с выбором цвета и без")
    @Test
    public void createOrderColorParamShouldBeCreatedTest() {
        OrderRequest orderRequest = getCreateOrderRequest(color);
        orderClient.createOrder(orderRequest).assertThat().statusCode(SC_CREATED).and().body("track", notNullValue());
    }
}
