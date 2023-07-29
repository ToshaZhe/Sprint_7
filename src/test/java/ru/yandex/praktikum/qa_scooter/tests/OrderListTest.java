package ru.yandex.praktikum.qa_scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.qa_scooter.client.OrderClient;
import ru.yandex.praktikum.qa_scooter.pojo.OrderRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @DisplayName("Получение списка заказов")
    @Test
    public void getOrdersListNotBeEmpty() {
        OrderRequest orderRequest = new OrderRequest();
        orderClient.getOrderList(orderRequest).assertThat().statusCode(SC_OK).and().body("orders", notNullValue());
    }
}
